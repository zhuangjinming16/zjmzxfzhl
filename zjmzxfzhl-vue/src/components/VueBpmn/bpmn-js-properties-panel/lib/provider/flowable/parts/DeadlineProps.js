'use strict';

var entryFactory = require('../../../factory/EntryFactory');
var cmdHelper = require('bpmn-js-properties-panel/lib/helper/CmdHelper');
var is = require('bpmn-js/lib/util/ModelUtil').is,
  getBusinessObject = require('bpmn-js/lib/util/ModelUtil').getBusinessObject;

module.exports = function(group, element, elementRegistry, bpmnFactory, translate) {
  if (!is(element, 'bpmn:UserTask')) {
    return;
  }

  function getAttribute(element, prop) {
    const bo = getBusinessObject(element);
    const value = bo.get(prop);
    return value;
  }

  group.entries.push(entryFactory.dateField({
    id: 'startTime',
    label: '开始时间',
    modelProperty: 'startTime',
    description: 'ISO 8601格式',
    get: function(element) {
      return {
        'startTime': getAttribute(element, 'startTime')
      }
    },
    set: function(element, values) {
      const bo = getBusinessObject(element);
      return cmdHelper.updateBusinessObject(element, bo, values);
    }
  }));

  
};
