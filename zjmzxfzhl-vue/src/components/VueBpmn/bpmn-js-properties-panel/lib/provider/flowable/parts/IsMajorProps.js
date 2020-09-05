'use strict';
var entryFactory = require('bpmn-js-properties-panel/lib/factory/EntryFactory');
var is = require('bpmn-js/lib/util/ModelUtil').is,
  getBusinessObject = require('bpmn-js/lib/util/ModelUtil').getBusinessObject;

module.exports = function(group, element, bpmnFactory, translate) {
  var businessObject = getBusinessObject(element);
  if (is(element, 'bpmn:UserTask')) {
    const isMajor = entryFactory.selectBox({
      id: 'isMajor',
      label: translate('必经节点'),
      modelProperty: 'isMajor',
      selectOptions: [
        { name: '', value: '' },
        { name: '是', value: '0' },
        { name: '否', value: '1' },
      ],
    });

    // 设置默认值
    if (!businessObject.get('isMajor')) {
      businessObject.$attrs['isMajor'] = '0';
    }

    group.entries = group.entries.concat(isMajor);
  }
};
