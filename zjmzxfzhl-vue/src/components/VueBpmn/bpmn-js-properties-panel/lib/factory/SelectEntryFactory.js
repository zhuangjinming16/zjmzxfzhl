'use strict';

var escapeHTML = require('../Utils').escapeHTML;

var domify = require('min-dom').domify,
    attr = require('min-dom').attr;

var forEach = require('lodash/forEach');

var entryFieldDescription = require('./EntryFieldDescription');


var isList = function(list) {
  return !(!list || Object.prototype.toString.call(list) !== '[object Array]');
};

var addEmptyParameter = function(list) {
  return list.concat([ { name: '', value: '' } ]);
};

var createOption = function(option) {
  return '<option value="' + option.value + '">' + option.name + '</option>';
};

/**
 * @param  {Object} options
 * @param  {string} options.id
 * @param  {string} [options.label]
 * @param  {Array<Object>} options.selectOptions
 * @param  {string} options.modelProperty
 * @param  {boolean} options.emptyParameter
 * @param  {function} options.disabled
 * @param  {function} options.hidden
 * @param  {Object} defaultParameters
 *
 * @return {Object}
 */
var selectbox = function(options, defaultParameters) {
  var resource = defaultParameters,
      label = options.label || resource.id,
      selectOptions = options.selectOptions || [ { name: '', value: '' } ],
      modelProperty = options.modelProperty,
      emptyParameter = options.emptyParameter,
      canBeDisabled = !!options.disabled && typeof options.disabled === 'function',
      canBeHidden = !!options.hidden && typeof options.hidden === 'function',
      description = options.description;


  if (emptyParameter) {
    selectOptions = addEmptyParameter(selectOptions);
  }

  const multiple = resource.multiple || '';
  resource.html =
    '<label for="flowable-' + escapeHTML(resource.id) + '"' +
    (canBeDisabled ? 'data-disable="isDisabled" ' : '') +
    (canBeHidden ? 'data-show="isHidden" ' : '') +
    '>' + escapeHTML(label) + '</label>' +
    '<select id="flowable-' + escapeHTML(resource.id) + '-select" '+ multiple +' name="' +
    escapeHTML(modelProperty) + '"' +
    (canBeDisabled ? 'data-disable="isDisabled" ' : '') +
    (canBeHidden ? 'data-show="isHidden" ' : '') +
    ' data-value>';

  if (isList(selectOptions)) {
    forEach(selectOptions, function(option) {
      resource.html += '<option value="' + escapeHTML(option.value) + '">' +
      (option.name ? escapeHTML(option.name) : '') + '</option>';
    });
  }

  resource.html += '</select>';

  // add description below select box entry field
  if (description && typeof options.showCustomInput !== 'function') {
    resource.html += entryFieldDescription(description);
  }

  /**
   * Fill the select box options dynamically.
   *
   * Calls the defined function #selectOptions in the entry to get the
   * values for the options and set the value to the inputNode.
   *
   * @param {djs.model.Base} element
   * @param {HTMLElement} entryNode
   * @param {EntryDescriptor} inputNode
   * @param {Object} inputName
   * @param {Object} newValue
   */
  resource.setControlValue = function(element, entryNode, inputNode, inputName, newValue) {
    if (typeof selectOptions === 'function') {
      var options = selectOptions(element, inputNode);
      if (Array.isArray(options)) {

        // remove existing options
        while (inputNode.firstChild) {
          inputNode.removeChild(inputNode.firstChild);
        }

        // add options
        forEach(options, function(option) {
          var template = domify(createOption(option));

          inputNode.appendChild(template);
        });


      } else {
        // 避免重复请求
        const inputValue = inputNode.value;
        if(!inputValue) {
          // 异步请求走这里
          options.then((results) => {
            while (inputNode.firstChild) {
              inputNode.removeChild(inputNode.firstChild);
            }
            // add options
            // 添加空选项
            var emptyTemplate = domify(createOption({name: '', value: ''}));
            inputNode.appendChild(emptyTemplate);
            forEach(results, function(results) {
              var template = domify(createOption(results));
              // 解决多选框选择多个值后，界面不展示多个值被选中问题
              let endNewValue = void 0;
              if(inputNode.multiple) {
                // 多选
                endNewValue = newValue ? newValue.split(','): [];
                if(endNewValue.findIndex(function(value, index, arr){
                  return value === template.value;
                }) >=0 ) {
                  attr(template, 'selected', 'selected');
                } else {
                  attr(template, 'selected', null);
                }
              } else {
                endNewValue = newValue;
                // 单选
                if (template.value === newValue) {
                  attr(template, 'selected', 'selected');
                } else {
                    attr(template, 'selected', null);
                }
              }

              inputNode.appendChild(template);
            });
          })
        }
      }
    }

    // set select value
    if (newValue !== undefined) {
      inputNode.value = newValue;
    }

  };

  if (canBeDisabled) {
    resource.isDisabled = function() {
      return options.disabled.apply(resource, arguments);
    };
  }

  if (canBeHidden) {
    resource.isHidden = function() {
      return !options.hidden.apply(resource, arguments);
    };
  }

  resource.cssClasses = ['bpp-dropdown'];

  return resource;
};

module.exports = selectbox;
