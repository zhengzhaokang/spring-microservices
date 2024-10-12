package com.microservices.otmp.system.common;

public class ConstantViewField {

    private ConstantViewField() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 模板如果没有维度，指定一个默认维度
     */
    public static final String DIMENSION_DEFAULT = "default";
    public static final String SYS_VIEW_FIELD_TYPE = "sys_view_field";
    public static final String VIEW_FIELD_CODE = "viewFieldCode";
    /**
     * 维度 选中所有值
     */
    public static final String ALL_VALUE = "All";
    /**
     * 截取字符串
     */
    public static final String ALL_VALUE_SUB = "All,";
    public static final String SORT_FIELD = "sortField";
    public static final String PAGE_NUM = "pageNum";
    public static final String PAGE_SIZE = "pageSize";
    public static final String CONDITION_OBJ_LIST = "conditionObjList";
    public static final String DIMENSIONS = "dimensions";

    public static final String PARENT_ID = "parentId";
    public static final String PARENT_ID_OLD = "ParentIdOld";
    public static final String POPOVER_BOUND_FIELD = "popoverBoundField";
    public static final String PAGE_KEY = "pageKey";
    public static final String PAGE_KEY_NAME = "pageKeyName";
    /**
     * 只有在页面用条件查询的时候才有用
     **/
    public static final String CONDITION = "condition";
    public static final String VIEW_TYPE = "viewType";
    public static final String DIV_KEY = "divKey";
    public static final String TYPE = "type";
    public static final String TABLE_NAME = "tableName";
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_TIME = "updateTime";
    public static final String CREATE_BY = "createBy";
    public static final String UPDATE_BY = "updateBy";
    public static final String FIELD_JSON = "fieldJson";
    public static final String SORT = "sort";
    public static final String ID = "id";
    public static final String STATUS = "status";

    public static final String TEMPLATE_ID = "templateId";
    public static final String TEMPLATE_FIELD_JSON = "templateFieldJson";
    public static final String TEMPLATE_NAME = "templateName";
    public static final String IT_CODE = "itCode";
    public static final String IT_CODE_DEF = "itCodeDef";
    public static final String IS_DEFAULT = "isDefault";

    public static final String ROW_ID = "rowId";
    public static final String PAGE_PARENT_ID = "pageParentId";
    public static final String DIFFERENT_TEMPLATE = "differentTemplate";
    public static final String TYPE_NAME = "typeName";
    public static final String STATUS_NAME = "statusName";
    public static final String MENU_NAME = "menuName";
    /**
     * 一级
     */
    public static final String LEVEL = "level";

    /**
     * copy 模板用的字段
     */
    public static final String PAGE_KEY_NEW = "pageKeyNew";
    public static final String VIEW_TYPE_NEW = "viewTypeNew";
    public static final String LABEL = "label";
    public static final String FIELD = "field";

    /**
     * 维度字段key
     */
    public static final String VIEW_FIELD_DIMENSIONS = "view_field_dimensions";

    /**
     * 字典business group KEY business_group
     */
    public static final String DICT_BUSINESS_GROUP = "business_group";

    /**
     * 字典business group KEY business_group
     */
    public static final String DICT_GEO_CODE = "geo_code";
    /**
     * template 切换使用
     */
    public static final String TEMPLATE_NAME_SHOW = "name";
    /**
     * 字段维度下拉参数key
     */
    public static final String DIMENSIONS_TYPE = "dimensionsType";

    public static final String GEO_CODE = "geoCode";
    public static final String BUSINESS_GROUP = "businessGroup";
    public static final String CHILDREN = "children";

    /**
     * 字段范围配置：不等于的字段赋值到 neq key里面
     */
    public static final String NEQ = "neq";
}
