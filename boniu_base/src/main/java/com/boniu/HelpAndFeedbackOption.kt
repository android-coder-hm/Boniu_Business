package com.boniu

enum class HelpAndFeedbackOption(val optionName: String, val optionType: String) {
    UI_PROBLEM("界面问题", "UI"),
    FUNCTION_PROBLEM("功能问题", "FUNCTION"),
    CONTENT_PROBLEM("内容问题", "CONTENT"),
    OTHER_PROBLEM("其他问题", "OTHER"),
    PRODUCE_SUGGEST("产品建议", "PROD_SUGGEST")
}