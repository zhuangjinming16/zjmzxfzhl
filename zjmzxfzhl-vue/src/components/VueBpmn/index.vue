<template>
    <el-container class="fm2-container">
        <el-main class="fm2-main">
            <el-container>
                <el-container class="center-container" direction="vertical">
                    <el-header class="btn-bar">
                        <el-button-group>
                            <el-button icon="el-icon-arrow-left" @click="handleUndo">撤回</el-button>
                            <el-button icon="el-icon-arrow-right" @click="handleRedo">重做</el-button>
                            <el-button icon="el-icon-circle-plus-outline" @click="handleZoom(0.2)">放大</el-button>
                            <el-button icon="el-icon-help" @click="handleZoom(0)">还原</el-button>
                            <el-button icon="el-icon-remove-outline" @click="handleZoom(-0.2)">缩小</el-button>
                            <el-button icon="el-icon-upload2" @click="importXml">导入</el-button>
                            <el-upload action="" :before-upload="openBpmn" style="display: none">
                                <el-button ref="importBtn" size="mini" icon="el-icon-folder-opened"/>
                            </el-upload>
                            <el-button icon="el-icon-download" @click="handleExportXmlAction">XML</el-button>
                            <el-button icon="el-icon-download" @click="handleExportSvgAction">SVG
                            </el-button>
                            <el-button icon="el-icon-tickets" @click="handlePreviewXml">预览</el-button>
                            <el-button icon="el-icon-delete" @click="handleClear">清空</el-button>

                            <el-button v-permission="'flowable:model:save,flowable:model:update'" icon="el-icon-upload"
                                       @click="handleSave">保存
                            </el-button>
                        </el-button-group>
                    </el-header>
                    <el-main>
                        <div class="containers" :style="{height: getContainerHeight}">
                            <div class="canvas" ref="canvas"/>
                            <el-dialog :visible.sync="xmlVisible" title="预览" fullscreen center>
                                <vue-ace-editor v-model="process.xml"
                                                @init="editorInit"
                                                lang="xml"
                                                theme="chrome"
                                                width="100%"
                                                height="calc(100vh - 214px)"
                                                :options="{wrap: true, readOnly: true}">
                                </vue-ace-editor>
                                <span slot="footer">
                                    <el-button icon="el-icon-document" type="primary" v-clipboard:copy="process.xml"
                                               v-clipboard:success="onCopy">复 制</el-button>
                                    <el-button icon="el-icon-close" @click="xmlVisible = false">关闭</el-button>
                                </span>
                            </el-dialog>
                        </div>
                    </el-main>
                </el-container>

                <el-aside class="widget-config-container">
                    <el-container>
                        <el-main class="config-content">
                            <div id="properties-panel"></div>
                        </el-main>
                    </el-container>
                </el-aside>
            </el-container>
        </el-main>
        <el-footer height="30px" style="font-weight: 600;">
            Powered by <a target="_blank" href="https://gitee.com/zjm16/zjmzxfzhl-cloud">Zjmzxfzhl-Cloud</a>
        </el-footer>
    </el-container>

</template>

<script>
    // 汉化
    import customTranslate from './translate/Translate.js'
    // bpmn-js 设计器
    // import BpmnModeler from './Modeler' // 上面说的index.js文件
    import BpmnModeler from 'bpmn-js/lib/Modeler'
    // 这里引入的是右侧属性栏这个框
    import propertiesPanelModule from './bpmn-js-properties-panel/lib' // 自定义的属性面板
    // 引入flowable的节点文件
    import flowableModdle from './bpmn-js-properties-panel/static/flowModel/flowable.json'
    // 而这个引入的是右侧属性栏里的内容
    import propertiesProviderModule from './bpmn-js-properties-panel/lib/provider/flowable'
    import './bpmn-js-properties-panel/styles/index.css' // 右边工具栏样式

    import VueAceEditor from 'vue2-ace-editor'
    import newXml from './resources/newDiagram.js'

    export default {
        props: {
            editor: {
                type: String,
                default: undefined,
            },
        },
        components: {VueAceEditor},
        data() {
            return {
                scale: 1,  //流程图比例（用于放大缩小）
                bpmnModeler: null,
                process: {
                    xml: '',
                    svg: ''
                },
                nodeProcessSelect: null,
                xmlVisible: false
            }
        },
        methods: {
            // bind SVG element height.
            getContainerHeight() {
                return (document.body.offsetHeight - 75) + 'px'
            },
            // init ace editor
            editorInit: function () {
                require('brace/ext/language_tools')
                require('brace/mode/xml')
                require('brace/theme/chrome')
            },
            getProcessElement() {
                return this.bpmnModeler.getDefinitions().rootElements[0]
            },
            getProcess() {
                const element = this.getProcessElement()
                return {
                    id: element.id,
                    name: element.name,
                    category: element.$attrs['flowable:processCategory']
                }
            },
            // 初始化流程图
            createNewDiagram(xml) {
                if (!xml) {
                    // 初始化XML文本
                    this.process.xml = newXml()
                } else {
                    this.process.xml = xml
                }
                // 将字符串转换成图显示出来
                this.bpmnModeler.importXML(this.process.xml, err => {
                    if (err) {
                        console.error(err)
                    } else {
                        // this.adjustPalette()
                    }
                })
            },
            // 当图发生改变的时候会调用这个函数，这个data就是图的xml
            setEncoded(type, data) {
                // 把xml转换为URI，下载要用到的
                const encodedData = encodeURIComponent(data)
                if (data) {
                    if (type === 'xml') {
                        // 获取到图的xml，保存就是把这个xml提交给后台
                        this.process.xml = data
                        return {
                            filename: this.process.name + '.bpmn20.xml',
                            href: "data:application/bpmn20-xml;charset=UTF-8," + encodedData,
                            data: data
                        }
                    }
                    if (type === 'svg') {
                        this.process.svg = data
                        return {
                            filename: this.process.name + '.bpmn20.svg',
                            href: "data:application/text/xml;charset=UTF-8," + encodedData,
                            data: data
                        }
                    }
                }
            },
            // 导入
            importXml() {
                this.$refs.importBtn.$el.click()
            },
            // 导入文件选择完成
            openBpmn(file) {
                const reader = new FileReader()
                reader.readAsText(file, 'utf-8')
                reader.onload = () => {
                    this.createNewDiagram(reader.result)
                }
                return false
            },
            /**
             * 下载xml/svg
             *  @param  type  类型  svg / xml
             *  @param  data  数据
             *  @param  name  文件名称
             */
            download(type, data, name) {
                let dataTrack = ''
                const a = document.createElement('a')
                switch (type) {
                    case 'xml':
                        dataTrack = 'bpmn'
                        break
                    case 'svg':
                        dataTrack = 'svg'
                        break
                    default:
                        break
                }
                name = name || `diagram.${dataTrack}`
                a.setAttribute('href', `data:application/bpmn20-xml;charset=UTF-8,${encodeURIComponent(data)}`)
                a.setAttribute('target', '_blank')
                a.setAttribute('dataTrack', `diagram:download-${dataTrack}`)
                a.setAttribute('download', name)
                document.body.appendChild(a)
                a.click()
                URL.revokeObjectURL(a.href) // 释放URL 对象
                document.body.removeChild(a)
            },
            // 导出XML文件
            handleExportXmlAction() {
                const _this = this
                this.bpmnModeler.saveXML({format: true}, function (err, xml) {
                    _this.download('xml', xml, _this.getProcess().name + '.bpmn20.xml')
                })
            },
            // 导出SVG文件
            handleExportSvgAction() {
                const _this = this
                this.bpmnModeler.saveSVG({format: true}, function (err, svg) {
                    _this.download('svg', svg, _this.getProcess().name + '.bpmn20.svg')
                })
            },
            // 预览
            handlePreviewXml() {
                this.bpmnModeler.saveXML({format: true}, (err, xml) => {
                    this.process.xml = xml
                    this.xmlVisible = true
                })
            },
            // 清空
            handleClear() {
                this.createNewDiagram()
            },
            // 保存
            handleSave() {
                const _this = this
                this.bpmnModeler.saveXML({format: true}, (err, xml) => {
                    _this.process.xml = xml
                    _this.$emit("save", _this.process.xml);
                })
            },
            // 复制成功
            onCopy() {
                this.$message.success('内容复制成功')
            },
            // 前进
            handleRedo() {
                this.bpmnModeler.get('commandStack').redo()
            },
            // 后退
            handleUndo() {
                this.bpmnModeler.get('commandStack').undo()
            },
            // 流程图放大缩小
            handleZoom(radio) {
                const newScale = !radio
                    ? 1.0 // 不输入radio则还原
                    : this.scale + radio <= 0.2 // 最小缩小倍数
                        ? 0.2
                        : this.scale + radio
                this.bpmnModeler.get('canvas').zoom(newScale)
                this.scale = newScale
            }
        },
        mounted() {
            const canvas = this.$refs.canvas
            // 生成实例
            this.bpmnModeler = new BpmnModeler({
                container: canvas,
                propertiesPanel: {
                    parent: '#properties-panel',
                },
                additionalModules: [
                    {translate: ['value', customTranslate]},
                    propertiesPanelModule,
                    propertiesProviderModule
                ],
                moddleExtensions: {
                    flowable: flowableModdle
                },
                height: '100%',
                width: '100%'
            })
            // 监听流程图改变事件
            const _this = this
            this.bpmnModeler.on("commandStack.changed", () => {
                _this.bpmnModeler.saveSVG({format: true}, function (err, svg) {
                    _this.setEncoded('svg', err ? null : svg)
                })
                _this.bpmnModeler.saveXML({format: true}, function (err, xml) {
                    _this.setEncoded('xml', err ? null : xml)
                })
            })
            // 新增流程定义
            this.createNewDiagram(this.editor)
        }
    }
</script>

<style lang="scss">
    /*左边工具栏以及编辑节点的样式*/
    @import "~bpmn-js/dist/assets/diagram-js.css";
    @import "~bpmn-js/dist/assets/bpmn-font/css/bpmn.css";
    @import "~bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css";
    @import "~bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css";

    aside {
        line-height: 1em;
        font-size: 14px;
    }

    $primary-color: #409EFF;
    $primary-background-color: #ecf5ff;

    .fm2-container {
        background: #fff;
        //height: 100%;
        height: calc(100vh - 84px);
        border: 1px solid #e0e0e0;

        .el-container {
            height: 100% !important;
        }

        & > .el-container {
            background: #fff;
        }
        .fm2-main {
            position: relative;

            & > .el-container {
                position: absolute;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
            }
        }
        main {
            padding: 0;
        }

        footer {
            height: 30px;
            line-height: 30px;
            border-top: 1px solid #e0e0e0;
            font-size: 12px;
            text-align: right;
            color: $primary-color;
            background: #fafafa;
            a {
                color: $primary-color;
            }
        }
    }

    .center-container {
        border-left: 1px solid #e0e0e0;
        border-right: 1px solid #e0e0e0;

        .btn-bar {
            height: 50px !important;
            font-size: 18px;
            border-bottom: solid 2px #e4e7ed;
            .el-button-group {
                margin-top: 4px;
            }
        }

        .el-main {
            padding: 0;
            position: relative;
            background: #fafafa;
        }
    }

    .widget-config-container {
        position: relative;

        .el-header {
            border-bottom: solid 2px #e4e7ed;
            padding: 0 5px;
        }

        .config-tab {
            height: 45px;
            line-height: 45px;
            display: inline-block;
            width: 145px;
            text-align: center;
            font-size: 14px;
            font-weight: 500;
            position: relative;
            cursor: pointer;

            &.active {
                border-bottom: solid 2px $primary-color;
            }
        }

        .config-content {
            padding: 10px;

            .el-form-item__label {
                padding: 0;
                font-weight: 500;
            }

            .el-form-item {
                border-bottom: solid 1px #e1e1e1;
                padding-bottom: 10px;
            }
        }

        .ghost {
            background: #fff;
            border: 1px dashed $primary-color;

            &::after {
                background: #fff;
                display: block;
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
            }
        }

        ul {
            margin: 0;
            padding: 0;
        }

        li.ghost {
            list-style: none;
            font-size: 0;
            display: block;
            position: relative;
        }
    }

    .containers {
        /*position: absolute;*/
        background-color: #ffffff;
        width: 100%;
        height: 100%;

        .canvas {
            width: 100%;
            height: 100%;
        }
        .panel {
            position: absolute;
            right: 0;
            top: 50px;
            width: 300px;
        }
        .bjs-powered-by {
            display: none;
        }
        .toolbar {
            position: absolute;
            top: 0;
            right: 320px;
            height: 40px;
            width: 600px;
            border: 1px solid red;
            a {
                text-decoration: none;
                margin: 5px;
                color: #409eff;
            }
        }
    }
</style>
