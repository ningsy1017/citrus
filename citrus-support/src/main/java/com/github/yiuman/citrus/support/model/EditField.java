package com.github.yiuman.citrus.support.model;

import com.github.yiuman.citrus.support.widget.Inputs;

/**
 * 编辑的字段，用于新增、编辑视图等
 *
 * @author yiuman
 * @date 2020/5/9
 */
public class EditField {

    /**
     * 文本
     */
    private String text;

    /**
     * 名称
     */
    private String name;

    /**
     * 对应的组件
     */
    private Object widget;

    /**
     * 检验规则
     */
    private String[] rules;

    public EditField() {
    }

    public EditField(String text, String name) {
        this.text = text;
        this.name = name;
        this.widget = new Inputs(text, name);
    }

    public EditField(String text, String name, Object widget) {
        this.text = text;
        this.name = name;
        this.widget = widget;
    }

    public EditField(String text, String name, Object widget, String[] rules) {
        this.text = text;
        this.name = name;
        this.widget = widget;
        this.rules = rules;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getWidget() {
        return widget;
    }

    public void setWidget(Object widget) {
        this.widget = widget;
    }

    public String[] getRules() {
        return rules;
    }

    public void setRules(String[] rules) {
        this.rules = rules;
    }
}