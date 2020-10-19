'use strict';

var entryFactory = require('../../../factory/EntryFactory'),
    getBusinessObject = require('bpmn-js/lib/util/ModelUtil').getBusinessObject,
    is = require('bpmn-js/lib/util/ModelUtil').is,
    utils = require('../../../Utils'),
    cmdHelper = require('../../../helper/CmdHelper');

module.exports = function (group, element, translate, options) {
    var businessObject = getBusinessObject(element);
    var description = options && options.description;
    var disabled = undefined
    if (is(element, 'bpmn:Process') || (is(element, 'bpmn:Participant') && businessObject.get('processRef'))) {
        disabled = function () {
            return false
        }
    }
    // Id
    group.entries.push(entryFactory.validationAwareTextField({
        id: 'id',
        label: translate('Id'),
        description: description && translate(description),
        modelProperty: 'id',
        disabled: disabled,
        getProperty: function (element) {
            return getBusinessObject(element).id;
        },
        setProperty: function (element, properties) {

            element = element.labelTarget || element;

            return cmdHelper.updateProperties(element, properties);
        },
        validate: function (element, values) {
            var idValue = values.id;

            var bo = getBusinessObject(element);

            var idError = utils.isIdValid(bo, idValue, translate);

            return idError ? {id: idError} : {};
        }
    }));

};
