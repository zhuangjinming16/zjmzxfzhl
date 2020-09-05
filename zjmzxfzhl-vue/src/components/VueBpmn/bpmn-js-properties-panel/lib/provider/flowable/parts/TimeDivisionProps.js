'use strict';

var entryFactory = require('../../../factory/EntryFactory');
var cmdHelper = require('bpmn-js-properties-panel/lib/helper/CmdHelper');
var elementsHelper = require('bpmn-js-properties-panel/lib/helper/ElementHelper');
var is = require('bpmn-js/lib/util/ModelUtil').is,
  domQuery = require('min-dom').query,
  getBusinessObject = require('bpmn-js/lib/util/ModelUtil').getBusinessObject;

module.exports = function(group, element, bpmnFactory, translate) {
  if (!is(element, 'bpmn:UserTask')) {
    return;
  }
  const bo = getBusinessObject(element);

  const node = entryFactory.timeField({
    id: 'warnDuration',
    label: '提醒时间',
    modelProperty: 'warnDuration',
    get: function(element) {
      let hour = '0';
      let minute = '0';
      const warnDuration = bo.get('warnDuration');
      if (warnDuration) {
        if (warnDuration.indexOf('H') > 0) {
          const warnDurationTemp = warnDuration.split('H');
          // 小时
          hour = warnDurationTemp[0];
          if (warnDuration.indexOf('M') > 0) {
            // 分钟
            const minute = warnDurationTemp[1].split('M')[0];
            return {
              'warnDuration-h': hour,
              'warnDuration-m': minute,
            };
          } else {
            return {
              'warnDuration-h': hour,
            };
          }
        } else {
          if (warnDuration.indexOf('M') > 0) {
            // 分钟
            const minute = warnDuration.split('M')[0];
            return {
              'warnDuration-m': minute,
            };
          } else {
            return {};
          }
        }
      }
      return {};
    },

    set: function(element, values) {
      const domHour = domQuery('input[id="warnDuration-h"');
      const domMinute = domQuery('input[id="warnDuration-m"');
      let newValue = '';
      if (domHour.value) {
        if (domMinute.value) {
          newValue = `${domHour.value}H${domMinute.value}M`;
        } else {
          newValue = `${domHour.value}H`;
        }
      } else if (domMinute.value) {
        newValue = `0H${domMinute.value}M`;
      }
      const attrs = {};
      attrs['warnDuration'] = newValue;

      return cmdHelper.updateBusinessObject(element, bo, attrs);
    },
    validate: function(element, values) {
      let validationResult = {};
      return validationResult;
      
    },
  });
  group.entries.push(node);
};
