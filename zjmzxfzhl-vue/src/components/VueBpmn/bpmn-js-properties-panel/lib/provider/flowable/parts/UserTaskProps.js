'use strict';

var is = require('bpmn-js/lib/util/ModelUtil').is,
    entryFactory = require('../../../factory/EntryFactory'),
    cmdHelper = require('../../../helper/CmdHelper');

var getBusinessObject = require('bpmn-js/lib/util/ModelUtil').getBusinessObject;
var getDicts = require('@/utils/util').getDicts

module.exports = function (group, element, translate) {
    if (is(element, 'flowable:Assignable')) {
        // Category
        // group.entries.push(entryFactory.textField({
        //     id : 'category',
        //     label : translate('Category'),
        //     modelProperty : 'category',
        // }));
        // Category
        group.entries.push(entryFactory.selectBox({
            id: 'category',
            label: translate('Category'),
            selectOptions: function (element) {
                return new Promise(function (resolve) {
                    getDicts('taskCategory').then(res => {
                        let data = []
                        res.data.taskCategory.forEach(item => {
                            if (item && item.value) {
                                let obj = {value: item.value, name: item.content}
                                data.push(obj)
                            }
                        })
                        resolve(data)
                    })
                });
            },
            modelProperty: 'category'
            // get: function (element) {
            //     return {category: getBusinessObject(element).category};
            // },
            // set: function (element, values) {
            //     const bo = getBusinessObject(element);
            //     return cmdHelper.updateBusinessObject(element, bo, values);
            // },
        }));

        // Assignee
        group.entries.push(entryFactory.textField({
            id: 'assignee',
            label: translate('Assignee'),
            modelProperty: 'assignee',
        }));

        // Candidate Users
        group.entries.push(entryFactory.textField({
            id: 'candidateUsers',
            label: translate('Candidate Users'),
            modelProperty: 'candidateUsers'
        }));

        // Candidate Groups
        group.entries.push(entryFactory.textField({
            id: 'candidateGroups',
            label: translate('Candidate Groups'),
            modelProperty: 'candidateGroups'
        }));

        // Due Date
        group.entries.push(entryFactory.textField({
            id: 'dueDate',
            description: translate('The due date as an EL expression (e.g. ${someDate} or an ISO date (e.g. 2015-06-26T09:54:00)'),
            label: translate('Due Date'),
            modelProperty: 'dueDate'
        }));

        // FollowUp Date
        group.entries.push(entryFactory.textField({
            id: 'followUpDate',
            description: translate('The follow up date as an EL expression (e.g. ${someDate} or an ' +
                'ISO date (e.g. 2015-06-26T09:54:00)'),
            label: translate('Follow Up Date'),
            modelProperty: 'followUpDate'
        }));

        // priority 优先级
        group.entries.push(entryFactory.textField({
            id: 'priority',
            label: translate('Priority'),
            modelProperty: 'priority'
        }));
    }
};
