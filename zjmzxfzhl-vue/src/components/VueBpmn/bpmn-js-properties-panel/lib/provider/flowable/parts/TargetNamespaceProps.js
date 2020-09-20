'use strict';

var entryFactory = require('../../../factory/EntryFactory'),
    getBusinessObject = require('bpmn-js/lib/util/ModelUtil').getBusinessObject,
    is = require('bpmn-js/lib/util/ModelUtil').is,
    utils = require('../../../Utils'),
    cmdHelper = require('../../../helper/CmdHelper');

module.exports = function (group, element, translate, options) {
    var businessObject = getBusinessObject(element);
    var description = options && options.description;

    if (is(element, 'bpmn:Process') || is(element, 'bpmn:Participant') && businessObject.get('processRef')) {
        group.entries.push(entryFactory.validationAwareTextField({
            id: 'targetNamespace',
            label: translate('Category'),
            description: description && translate(description),
            modelProperty: 'targetNamespace',
            getProperty: function (element) {
                return getBusinessObject(element).$parent.targetNamespace;
            },
            setProperty: function (element, properties) {
                element = element.labelTarget || element;
                element = getBusinessObject(element).$parent;
                return cmdHelper.updateBusinessObject(element, element, properties);
            },
            validate: function (element, values) {
                // var VALIDATE_REGEX = /^[a-z_][\w-.]*$/i;
                // var valid = VALIDATE_REGEX.test(values.targetNamespace);
                // var error =  valid ? false: translate('Invalid string');
                // return error ? {targetNamespace: error} : {};
                return {}
            }
        }));
    }
};
