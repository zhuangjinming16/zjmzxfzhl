'use strict';

var cmdHelper = require('../../../helper/CmdHelper'),
  entryFactory = require('../../../factory/EntryFactory');
var is = require('bpmn-js/lib/util/ModelUtil').is;

var getBusinessObject = require('bpmn-js/lib/util/ModelUtil').getBusinessObject;


module.exports = function(group, element, bpmnFactory, translate) {

  if(!is(element, 'bpmn:UserTask')) {
    return;
  }

  function getData() {
    return new Promise(function (resolve, reject) {
      setTimeout(() => {
        const data = [
          {name: '张三', value: 'zhangsan'},
          {name: '李四', value: 'lisi'}
        ]
        console.log('进入异步方法里');
        resolve(data)
      }, 2000);
      console.log('先执行这里');
    });
  }

  function getAttribute(element, prop) {
    let attr = {};
    const bo = getBusinessObject(element);
    var value = bo.get(prop);
    attr[prop] = value;
    return attr;
  }

  group.entries.push(
    entryFactory.selectBox({
      id: 'assigneeList',
      label: translate('受理人'),
      selectOptions:
        function(element) {
          return getData();
        },
      modelProperty: 'assigneeList',
      multiple: 'multiple', // 加上这个方法变成多选下拉框
      get: function(element) {
        var attr = getAttribute(element, 'assigneeList');
        return attr;
      },

      set: function(element, values) {
        const bo = getBusinessObject(element);
        return cmdHelper.updateBusinessObject(element, bo, values);
      },
    }),
  );

};
