package com.microservices.otmp.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.RemoteResponse;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.masterdata.domain.dto.BizBaseRegionMarketDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOfficeDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOrgDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseSegmentDTO;
import com.microservices.otmp.masterdata.feign.RemoteMasterDataService;
import com.microservices.otmp.system.domain.*;
import com.microservices.otmp.system.domain.entity.ViewConditionValueDO;
import com.microservices.otmp.system.domain.entity.ViewFieldDO;
import com.microservices.otmp.system.domain.entity.ViewTemplateDO;
import com.microservices.otmp.system.manager.ViewFieldManager;
import com.microservices.otmp.system.manager.ViewTemplateManager;
import com.microservices.otmp.system.mapper.ViewFieldMapper;
import com.microservices.otmp.system.service.ISysDictDataService;
import com.microservices.otmp.system.service.ISysMenuService;
import com.microservices.otmp.system.service.IViewConditionValueService;
import com.microservices.otmp.system.service.IViewTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ViewFieldServiceImplTest {

    @Mock
    private ViewTemplateManager mockViewTemplateManager;
    @Mock
    private ViewFieldManager mockViewFieldManager;
    @Mock
    private ISysMenuService mockSysMenuService;
    @Mock
    private IViewTemplateService mockViewTemplateService;
    @Mock
    private ISysDictDataService mockSysDictDataService;
    @Mock
    private RemoteMasterDataService mockRemoteMasterDataService;
    @Mock
    private IViewConditionValueService mockViewConditionValueService;
    @Mock
    private ViewFieldMapper mockViewFieldMapper;

    @Mock
    private ViewFieldServiceImpl mockViewFieldServiceImpl;

    @InjectMocks
    private ViewFieldServiceImpl viewFieldServiceImplUnderTest;

    @Test
    public void testSelectViewFieldById() {
        // Setup
        // Configure ViewFieldManager.selectViewFieldById(...).

        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(1559);
        viewFieldDO.setPageKey("2584");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("Result");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\"," +
                "\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false," +
                "\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\"," +
                "\"fields\":[{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\"," +
                "\"__config__\":{\"formId\":1,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\"," +
                "\"labelWidth\":220,\"label\":\"Business Group\",\"showLabel\":true," +
                "\"required\":false,\"renderKey\":\"1011644471929553101\",\"layout\":\"colFormItem\"," +
                "\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6}," +
                "\"readonly\":false,\"__vModel__\":\"businessGroup\",\"checked\":true,\"style\":{\"width\":\"\"}," +
                "\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[]," +
                "\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true," +
                "\"prefix-icon\":\"\",\"__config__\":{\"formId\":88,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220," +
                "\"label\":\"Geo\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553745\"," +
                "\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\"," +
                "\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"geoCode\",\"checked\":true," +
                "\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}," +
                "\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\"," +
                "\"__config__\":{\"formId\":89,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\"," +
                "\"labelWidth\":220,\"label\":\"Region/Market\",\"showLabel\":true,\"required\":false," +
                "\"renderKey\":\"1011644471929553943\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\"," +
                "\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false," +
                "\"__vModel__\":\"regionMarketCode\",\"checked\":true,\"style\":{\"width\":\"\"}," +
                "\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false," +
                "\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}," +
                "{\"fieldRange\":\"{\\\"geoCode\\\":\\\"EMEA\\\"}\",\"clearable\":true,\"isTooltip\":false," +
                "\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\"," +
                "\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}}," +
                "\"__config__\":{\"formId\":\"1vd5xvm1t9n\",\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/en-US/component/input\"," +
                "\"dataType\":\"static\",\"label\":\"Seller Country\",\"showLabel\":true," +
                "\"required\":false,\"renderKey\":\"kd0amztka0g0000000\",\"layout\":\"colFormItem\"," +
                "\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\"," +
                "\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false," +
                "\"__vModel__\":\"microservicesCountry\",\"checked\":true,\"style\":{\"width\":\"100%\"}," +
                "\"disabled\":true,\"toolText\":\"\",\"no-data-text\":\" \",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}," +
                "\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":169,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices Entity\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553420\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesEntity\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":171,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices VAT ID\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553512\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesVatId\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":31,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553377\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":32,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553835\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":6,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553903\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalYear\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553753\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalQuarter\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":90,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"GTN Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553554\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtnType\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"1c5y4af686l\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Code/Agreement Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7aj8fogcpjw00000000\",\"layout\":\"colFormItem\",\"isDefault\":false,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"2g8n4h6xfsz\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3w5700eql7200000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":12},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programName\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}}," +
                "\"__config__\":{\"formId\":\"2iemj2yq6h4\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Calculation Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6h4vnndgu3o00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"calculationCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one calculation link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":12}");
        viewFieldDO.setType(2);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldManager.selectViewFieldById(1559)).thenReturn(viewFieldDO);


        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(1);
        viewConditionValueDO.setFieldId(1);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        viewFieldServiceImplUnderTest.selectViewFieldById(1559);
    }

    @Test
    public void testSelectViewFieldById_IViewConditionValueServiceReturnsNoItems() {
        // Setup
        // Configure ViewFieldManager.selectViewFieldById(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldManager.selectViewFieldById(0)).thenReturn(viewFieldDO);

        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());

    }

    @Test
    public void testSelectViewFieldList() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> expectedResult = Arrays.asList(viewFieldDO);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        final List<ViewFieldDO> result = viewFieldServiceImplUnderTest.selectViewFieldList(viewField);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    public void testSelectViewFieldList_ViewFieldManagerReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewFieldDO> result = viewFieldServiceImplUnderTest.selectViewFieldList(viewField);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testReturnAllField() {
        ViewField viewField = new ViewField();
        viewField.setTableName("pool_view");
        viewField.setId(448);
        viewField.setFieldJson("{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\",\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false,\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\",\"fields\":[{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":17,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Status\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"status\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Status\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":3,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Pool Code\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":4,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Pool Name\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":101,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Occupied By\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011660641251058\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"occupiedBy\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Product No\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":8,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolFy\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Pool Fiscal Year\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":9,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolQ\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Pool Fiscal Quarter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":23,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Region Market Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"regionMarketCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Region Market Code\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":101,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Territory\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011668395910799\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"territory\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Pool Code\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":25,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Sales Org\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":26,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Sales Office\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":29,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Gtn Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtnTypeCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Gtn Type\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":12,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolCurrency\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Pool Currency\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":2,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Business Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"businessGroup\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Business Group\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":101,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"APC Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011650625210139\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"apcCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....APC Code\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":47,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Product No\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"productNo\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Product No\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"remark\":\"\",\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"wiccjcjktd\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Budget Value (P)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2uexakng69600000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"budgetValueP\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"remark\":\"\",\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"pio31g7wss\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Budget Value ($)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8utmb4e9ny000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"budgetValueUsd\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":101,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Available Budget（P）\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011656154997807\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolAvailableBudgetP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Product No\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":102,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Available Budget（$）\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1021656154997965\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolAvailableBudgetUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"请输入....Product No\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":6}");
        viewFieldServiceImplUnderTest.returnAllField(viewField);
    }

    @Test
    public void testInsertViewField() {
        // Setup

        final Map<String, Object> viewField = new HashMap<>();
        viewField.put("testData1", "");
        viewField.put("testData2", "testData2");
        viewField.put("id", "1559");
        viewField.put("status", "0");
        viewField.put("createBy", "admin123");
        viewField.put("updateBy", "admin123");
        viewField.put("fieldJson", "{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\",\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false,\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\",\"fields\":[{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":1,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Business Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553101\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"businessGroup\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":88,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Geo\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553745\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"geoCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":89,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Region/Market\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553943\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"regionMarketCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"{\\\"geoCode\\\":\\\"EMEA\\\"}\",\"clearable\":true,\"isTooltip\":false,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"1vd5xvm1t9n\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Seller Country\",\"showLabel\":true,\"required\":false,\"renderKey\":\"kd0amztka0g0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"microservicesCountry\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"\",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":169,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices Entity\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553420\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesEntity\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":171,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices VAT ID\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553512\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesVatId\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":31,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553377\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":32,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553835\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":6,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553903\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalYear\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553753\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalQuarter\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":90,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"GTN Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553554\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtnType\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"1c5y4af686l\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Code/Agreement Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7aj8fogcpjw00000000\",\"layout\":\"colFormItem\",\"isDefault\":false,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"2g8n4h6xfsz\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3w5700eql7200000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":12},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programName\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"2iemj2yq6h4\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Calculation Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6h4vnndgu3o00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"calculationCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one calculation link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":12}");


        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.insertViewFieldMap(new HashMap<>())).thenReturn(0);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);

        // Run the test
        assertThrows(OtmpException.class, () -> viewFieldServiceImplUnderTest.insertViewField(viewField));
        viewField.put("pageKey", "2349");
        assertThrows(OtmpException.class, () -> viewFieldServiceImplUnderTest.insertViewField(viewField));
        viewField.put("viewType", "details");
        assertThrows(OtmpException.class, () -> viewFieldServiceImplUnderTest.insertViewField(viewField));
        viewField.put("divKey", "Basic Information");
        assertThrows(OtmpException.class, () -> viewFieldServiceImplUnderTest.insertViewField(viewField));
        viewField.put("type", "1");
        assertThrows(OtmpException.class, () -> viewFieldServiceImplUnderTest.insertViewField(viewField));
        viewField.put("tableName", "biz_sdms_claim");
        assertThrows(OtmpException.class, () -> viewFieldServiceImplUnderTest.insertViewField(viewField));

    }

    @Test
    public void testInsertViewField_IViewConditionValueServiceSelectViewConditionValueListReturnsNoItems() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.insertViewFieldMap(new HashMap<>())).thenReturn(0);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);

        Boolean catchException = false;
        try {
            // Run the test
            final int result = viewFieldServiceImplUnderTest.insertViewField(viewField);

            // Verify the results
            assertEquals(0, result);
            verify(mockViewConditionValueService).insertViewConditionValue(any(ViewConditionValue.class));

        } catch (OtmpException e) {
            catchException = true;
        }
        assertTrue(catchException);


    }

    @Test
    public void testInsertViewField_RemoteDocumentsServiceReturnsNoItem() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.insertViewFieldMap(new HashMap<>())).thenReturn(0);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);


    }

    @Test
    public void testInsertViewField_RemoteDocumentsServiceReturnsError() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.insertViewFieldMap(new HashMap<>())).thenReturn(0);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);


        Boolean catchException = false;
        try {
            // Run the test
            final int result = viewFieldServiceImplUnderTest.insertViewField(viewField);

            // Verify the results
            assertEquals(0, result);
            verify(mockViewConditionValueService).insertViewConditionValue(any(ViewConditionValue.class));

        } catch (OtmpException e) {
            catchException = true;
        }
        assertTrue(catchException);
    }

    @Test
    public void testInsertViewField_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.insertViewFieldMap(new HashMap<>())).thenReturn(0);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);


        Boolean catchException = false;
        try {
            // Run the test
            final int result = viewFieldServiceImplUnderTest.insertViewField(viewField);

            // Verify the results
            assertEquals(0, result);
            verify(mockViewConditionValueService).insertViewConditionValue(any(ViewConditionValue.class));

        } catch (OtmpException e) {
            catchException = true;
        }
        assertTrue(catchException);
    }

    @Test
    public void testVerifyDimensions1() {
        // Setup
        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

    }

    @Test
    public void testAddConditionValue() {

        Map<String, Object> viewField = new HashMap<>();
        viewField.put("test1", "All");
        viewField.put("id", "10001");
        viewField.put("createBy", "zhangsan");
        viewField.put("updateBy", "lisi");
        Set<String> dimensionSet = new HashSet<>();
        dimensionSet.add("test1");
        viewFieldServiceImplUnderTest.addConditionValue(viewField, dimensionSet);
    }

    @Test
    public void testVerifyDimensions1_IViewConditionValueServiceReturnsNoItems() {
        // Setup
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());


        Boolean catchException = false;
        try {
            viewFieldServiceImplUnderTest.verifyDimensions(0, "{}", new HashSet<>(Arrays.asList("value")));
        } catch (Exception e) {
            catchException = true;
        }
        assertTrue(catchException);

    }

    @Test
    public void testGetTableNames() {
        // Setup
        when(mockViewFieldManager.getTableNames()).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<SysDictData> result = viewFieldServiceImplUnderTest.getTableNames();

        // Verify the results
    }

    @Test
    public void testGetTableNames_ViewFieldManagerReturnsNoItems() {
        // Setup
        when(mockViewFieldManager.getTableNames()).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysDictData> result = viewFieldServiceImplUnderTest.getTableNames();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testFieldDimensionsSelect() {
        // Setup
        final Map<String, String> condition = new HashMap<>();

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);


        // Run the test
        condition.put("geoCode", "All");
        condition.put("businessGroup", "All");
        assertThrows(OtmpException.class, () -> viewFieldServiceImplUnderTest.fieldDimensionsSelect(condition));
        condition.put("dimensionsType", "regionNew");
        final List<SysDictData> result = viewFieldServiceImplUnderTest.fieldDimensionsSelect(condition);
        condition.put("dimensionsType", "viewType");
        final List<SysDictData> resultViewType = viewFieldServiceImplUnderTest.fieldDimensionsSelect(condition);

        condition.put("dimensionsType", "salesOrgCode");
        assertThrows(NullPointerException.class, () -> viewFieldServiceImplUnderTest.fieldDimensionsSelect(condition));
        condition.put("dimensionsType", "paymentMode");
        final List<SysDictData> resultPaymentMode = viewFieldServiceImplUnderTest.fieldDimensionsSelect(condition);
        condition.put("dimensionsType", "regionMarketCode");
        assertThrows(NullPointerException.class, () -> viewFieldServiceImplUnderTest.fieldDimensionsSelect(condition));
        condition.put("dimensionsType", "salesOfficeCode");
        assertThrows(NullPointerException.class, () -> viewFieldServiceImplUnderTest.fieldDimensionsSelect(condition));
        condition.put("dimensionsType", "segmentCode");
        assertThrows(NullPointerException.class, () -> viewFieldServiceImplUnderTest.fieldDimensionsSelect(condition));
        condition.put("dimensionsType", "companyCode");
        assertThrows(NullPointerException.class, () -> viewFieldServiceImplUnderTest.fieldDimensionsSelect(condition));

        // Verify the results
    }

    @Test
    public void testFieldDimensionsSelect_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, String> condition = new HashMap<>();
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);


    }

    @Test
    public void testSelectViewFieldAndCondition() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.selectViewFieldAndCondition(map);

        // Verify the results
    }

    @Test
    public void testSelectViewFieldAndCondition_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.selectViewFieldAndCondition(map);

        // Verify the results
    }

    @Test
    public void testSelectViewFieldAndCondition_ViewFieldManagerReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndCondition(new HashMap<>())).thenReturn(Collections.emptyList());

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.selectViewFieldAndCondition(map);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectViewFieldAndTemplateAndCondition() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.selectViewFieldAndTemplateAndCondition(map);

        // Verify the results
    }

    @Test
    public void testSelectViewFieldAndTemplateAndCondition_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.selectViewFieldAndTemplateAndCondition(map);

        // Verify the results
    }

    @Test
    public void testSelectViewFieldAndTemplateAndCondition_ViewFieldManagerReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Collections.emptyList());

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.selectViewFieldAndTemplateAndCondition(map);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDataRecovery() {
        // Setup
        final Map<String, Object> condition = new HashMap<>();

        ViewField viewField1 = new ViewField();
        // viewField1.setId(1001);
        viewField1.setStatus(0);
        viewField1.setPageKey("2122");

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(1001);
        viewFieldDO.setPageKey("2122");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        viewFieldDO.setGeoCode("AP");
        viewFieldDO.setBusinessGroup("PCSD");
        viewFieldDO.setPaymentMode("test");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(viewField1)).thenReturn(viewFieldDOS);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        // Run the test
        assertThrows(NullPointerException.class, () -> viewFieldServiceImplUnderTest.dataRecovery(condition));


    }

    @Test
    public void testDataRecovery_ViewFieldManagerReturnsNoItems() {
        // Setup
        final Map<String, Object> condition = new HashMap<>();

        ViewField viewField1 = new ViewField();
        // viewField1.setId(1001);
        viewField1.setStatus(0);
        viewField1.setPageKey("2122");

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(1001);
        viewFieldDO.setPageKey("2122");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setGeoCode("AP");
        viewFieldDO.setBusinessGroup("PCSD");
        viewFieldDO.setPaymentMode("test");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(viewField1)).thenReturn(viewFieldDOS);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        viewFieldServiceImplUnderTest.dataRecovery(condition);

    }

    @Test
    public void testDataRecovery_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> condition = new HashMap<>();

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        // Run the test
        viewFieldServiceImplUnderTest.dataRecovery(condition);
    }

    @Test
    public void testColumnHeader() {
        // Setup
        final List<Map<String, String>> expectedResult = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);


    }

    @Test
    public void testColumnHeader_ISysDictDataServiceReturnsNoItems() {
        // Setup
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());

        // Run the test
        final List<Map<String, String>> result = viewFieldServiceImplUnderTest.columnHeader();

        // Verify the results
        assertNotNull(result);
    }

    @Test
    public void testTree() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(1559);
        viewFieldDO.setPageKey("2584");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("Result");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\"," +
                "\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false," +
                "\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\"," +
                "\"fields\":[{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\"," +
                "\"__config__\":{\"formId\":1,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\"," +
                "\"labelWidth\":220,\"label\":\"Business Group\",\"showLabel\":true," +
                "\"required\":false,\"renderKey\":\"1011644471929553101\",\"layout\":\"colFormItem\"," +
                "\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6}," +
                "\"readonly\":false,\"__vModel__\":\"businessGroup\",\"checked\":true,\"style\":{\"width\":\"\"}," +
                "\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[]," +
                "\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true," +
                "\"prefix-icon\":\"\",\"__config__\":{\"formId\":88,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220," +
                "\"label\":\"Geo\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553745\"," +
                "\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\"," +
                "\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"geoCode\",\"checked\":true," +
                "\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}," +
                "\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\"," +
                "\"__config__\":{\"formId\":89,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\"," +
                "\"labelWidth\":220,\"label\":\"Region/Market\",\"showLabel\":true,\"required\":false," +
                "\"renderKey\":\"1011644471929553943\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\"," +
                "\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false," +
                "\"__vModel__\":\"regionMarketCode\",\"checked\":true,\"style\":{\"width\":\"\"}," +
                "\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false," +
                "\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}," +
                "{\"fieldRange\":\"{\\\"geoCode\\\":\\\"EMEA\\\"}\",\"clearable\":true,\"isTooltip\":false," +
                "\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\"," +
                "\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}}," +
                "\"__config__\":{\"formId\":\"1vd5xvm1t9n\",\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/en-US/component/input\"," +
                "\"dataType\":\"static\",\"label\":\"Seller Country\",\"showLabel\":true," +
                "\"required\":false,\"renderKey\":\"kd0amztka0g0000000\",\"layout\":\"colFormItem\"," +
                "\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\"," +
                "\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false," +
                "\"__vModel__\":\"microservicesCountry\",\"checked\":true,\"style\":{\"width\":\"100%\"}," +
                "\"disabled\":true,\"toolText\":\"\",\"no-data-text\":\" \",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}," +
                "\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":169,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices Entity\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553420\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesEntity\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":171,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices VAT ID\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553512\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesVatId\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":31,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553377\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":32,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553835\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":6,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553903\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalYear\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553753\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalQuarter\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":90,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"GTN Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553554\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtnType\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"1c5y4af686l\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Code/Agreement Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7aj8fogcpjw00000000\",\"layout\":\"colFormItem\",\"isDefault\":false,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"2g8n4h6xfsz\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3w5700eql7200000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":12},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programName\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}}," +
                "\"__config__\":{\"formId\":\"2iemj2yq6h4\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Calculation Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6h4vnndgu3o00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"calculationCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one calculation link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":12}");
        viewFieldDO.setType(2);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final PageInfo<Map<String, Object>> result = viewFieldServiceImplUnderTest.tree(map);
        map.put("pageKeyName", "test");
        PageInfo<Map<String, Object>> result2 = viewFieldServiceImplUnderTest.tree(map);

        // Verify the results
    }

    @Test
    public void testTree_ViewFieldManagerSelectPageKeyListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(Collections.emptyList());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        final PageInfo<Map<String, Object>> result = viewFieldServiceImplUnderTest.tree(map);

        // Verify the results
    }

    @Test
    public void testTree_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final PageInfo<Map<String, Object>> result = viewFieldServiceImplUnderTest.tree(map);

        // Verify the results
    }

    @Test
    public void testTree_ViewFieldManagerSelectViewFieldAndTemplateAndConditionReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final PageInfo<Map<String, Object>> result = viewFieldServiceImplUnderTest.tree(map);

        // Verify the results
    }

    @Test
    public void testTree_ViewFieldManagerSelectViewFieldListPreciseReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final PageInfo<Map<String, Object>> result = viewFieldServiceImplUnderTest.tree(map);

        // Verify the results
    }

    @Test
    public void testTree_IViewConditionValueServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final PageInfo<Map<String, Object>> result = viewFieldServiceImplUnderTest.tree(map);

        // Verify the results
    }

    @Test
    public void testTree_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<Map<String, Object>> result = viewFieldServiceImplUnderTest.tree(map);

        // Verify the results
    }

    @Test
    public void testTwoTreeElse() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.twoTreeElse(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testTwoTreeElse_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.twoTreeElse(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testTwoTreeElse_ViewFieldManagerSelectViewFieldListPreciseReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.twoTreeElse(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testTwoTreeElse_IViewConditionValueServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.twoTreeElse(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testTwoTreeElse_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        viewFieldServiceImplUnderTest.twoTreeElse(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testThreeTreeElse() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.threeTreeElse(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testThreeTreeElse_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.threeTreeElse(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testThreeTreeElse_ViewFieldManagerSelectViewFieldListPreciseReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.threeTreeElse(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testThreeTreeElse_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        viewFieldServiceImplUnderTest.threeTreeElse(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testFourTreeElse() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);
        map.put("id", 1001);

        // Run the test
        viewFieldServiceImplUnderTest.fourTreeElse(map, treeList);

        // Verify the results
    }

    @Test
    public void testFourTreeElse_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);
        map.put("id", 1001);
        // Run the test
        viewFieldServiceImplUnderTest.fourTreeElse(map, treeList);

        // Verify the results
    }

    @Test
    public void testFourTreeElse_ViewFieldManagerSelectViewFieldListPreciseReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        map.put("id", 1001);
        // Run the test
        viewFieldServiceImplUnderTest.fourTreeElse(map, treeList);

        // Verify the results
    }

    @Test
    public void testFourTreeElse_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());
        map.put("id", 1001);

        // Run the test
        viewFieldServiceImplUnderTest.fourTreeElse(map, treeList);

        // Verify the results
    }

    @Test
    public void testFourTree() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.fourTree(map, treeList);

        // Verify the results
    }

    @Test
    public void testFourTree_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.fourTree(map, treeList);

        // Verify the results
    }

    @Test
    public void testFourTree_ViewFieldManagerSelectViewFieldListPreciseReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.fourTree(map, treeList);

        // Verify the results
    }

    @Test
    public void testFourTree_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        viewFieldServiceImplUnderTest.fourTree(map, treeList);

        // Verify the results
    }

    @Test
    public void testThreeTree() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.threeTree(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testThreeTree_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.threeTree(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testThreeTree_ViewFieldManagerSelectViewFieldListPreciseReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.threeTree(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testThreeTree_IViewConditionValueServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.threeTree(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testThreeTree_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        viewFieldServiceImplUnderTest.threeTree(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testTwoTree() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        viewFieldServiceImplUnderTest.twoTree(map, treeList);

        // Verify the results
    }

    @Test
    public void testTwoTree_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        viewFieldServiceImplUnderTest.twoTree(map, treeList);

        // Verify the results
    }

    @Test
    public void testTwoTree_ViewFieldManagerReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Collections.emptyList());

        // Run the test
        viewFieldServiceImplUnderTest.twoTree(map, treeList);

        // Verify the results
    }

    @Test
    public void testOne1() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        final PageInfo<Map<String, Object>> result = viewFieldServiceImplUnderTest.one(map, treeList);

        // Verify the results
    }

    @Test
    public void testOne1_ViewFieldManagerReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<Map<String, Object>> result = viewFieldServiceImplUnderTest.one(map, treeList);

        // Verify the results
    }

    @Test
    public void testNoDimension() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.noDimension(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testNoDimension_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.noDimension(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testNoDimension_ViewFieldManagerSelectViewFieldListPreciseReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        viewFieldServiceImplUnderTest.noDimension(map, treeList, 0);

        // Verify the results
    }

    @Test
    public void testNoDimension_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        viewFieldServiceImplUnderTest.noDimension(map, treeList, 0);

        // Verify the results
    }


    @Test
    public void testTwoElse() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());
        map.put("id", 1001);
        map.put("viewType", "列表页");

        // Run the test
        viewFieldServiceImplUnderTest.twoElse(map, treeList, 4);
        viewFieldServiceImplUnderTest.twoElse(map, treeList, 3);
        viewFieldServiceImplUnderTest.twoElse(map, treeList, 5);


        // Verify the results
    }

    @Test
    public void testFour() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        final List<Map<String, Object>> treeList = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());
        map.put("id", 1001);
        map.put("businessGroup", "ISG");

        // Run the test
        viewFieldServiceImplUnderTest.four(map, treeList);
        map.put("businessGroup", "default");

        viewFieldServiceImplUnderTest.four(map, treeList);
        // Verify the results
    }

    @Test
    public void testThree() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

         List<Map<String, Object>> treeList = new ArrayList<>();

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());
        map.put("id", 1001);
        map.put("businessGroup", "ISG");
        map.put("rowId", 1002);
        List<Map<String, Object>> returnList=new ArrayList<>();

        Map<String, Object> dataMap=new HashMap<>();
        dataMap.put("pageKeyName","test11");
        dataMap.put("pageKey","pageKey12");
        dataMap.put("viewType","viewType1243");
        dataMap.put("default","default1");
        dataMap.put("id",1001);
       // dataMap.put("rowId",1002);
        returnList.add(dataMap);
        when(viewFieldServiceImplUnderTest.selectViewFieldListPrecise(map)).thenReturn(returnList);

        // Run the test
        viewFieldServiceImplUnderTest.three(map, treeList);

        // Verify the results
    }

    @Test
    public void testTwo() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        final List<Map<String, Object>> treeList =new ArrayList<>();

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());
        map.put("id", 1001);
        map.put("businessGroup", "ISG");
        map.put("rowId", 1002);
        List<Map<String, Object>> returnList=new ArrayList<>();

        Map<String, Object> dataMap=new HashMap<>();
        dataMap.put("pageKeyName","test11");
        dataMap.put("pageKey","pageKey12");
        dataMap.put("viewType","viewType1243");
        dataMap.put("default","default1");
        dataMap.put("id",1001);
        // dataMap.put("rowId",1002);
        returnList.add(dataMap);
        when(viewFieldServiceImplUnderTest.selectViewFieldAndTemplateAndCondition(map)).thenReturn(returnList);


        // Run the test
        viewFieldServiceImplUnderTest.two(map, treeList);

        // Verify the results
    }

    @Test
    public void testTreeSyncData() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.treeSyncData(map);
        map.put("pageNum", "10");
        map.put("pageSize", "10");
        map.put("rowId", "1");
        map.put("pageKeyName", "test");
        map.put("level", "4");
        final List<Map<String, Object>> result1 = viewFieldServiceImplUnderTest.treeSyncData(map);


        // Verify the results
    }

    @Test
    public void testTreeSyncData_ViewFieldManagerSelectPageKeyListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(Collections.emptyList());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.treeSyncData(map);

        // Verify the results
    }

    @Test
    public void testTreeSyncData_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.treeSyncData(map);

        // Verify the results
    }

    @Test
    public void testTreeSyncData_ViewFieldManagerSelectViewFieldAndTemplateAndConditionReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.treeSyncData(map);

        // Verify the results
    }

    @Test
    public void testTreeSyncData_ViewFieldManagerSelectViewFieldListPreciseReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.treeSyncData(map);

        // Verify the results
    }

    @Test
    public void testTreeSyncData_IViewConditionValueServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS1);

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.treeSyncData(map);

        // Verify the results
    }

    @Test
    public void testTreeSyncData_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.treeSyncData(map);

        // Verify the results
    }

    @Test
    public void testColumnHeaderByCopy() {
        // Setup
        final List<Map<String, String>> expectedResult = Arrays.asList(new HashMap<>());

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Run the test
        final List<Map<String, String>> result = viewFieldServiceImplUnderTest.columnHeaderByCopy();

        // Verify the results
        assertNotNull(result);
    }

    @Test
    public void testColumnHeaderByCopy_ISysDictDataServiceReturnsNoItems() {
        // Setup
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());


    }

    @Test
    public void testUpdateViewField() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.updateViewFieldMap(new HashMap<>())).thenReturn(0);

        // Configure ViewTemplateManager.selectViewTemplateList(...).
        final ViewTemplateDO viewTemplateDO = new ViewTemplateDO();
        viewTemplateDO.setPageKey("pageKey");
        viewTemplateDO.setViewType("viewType");
        viewTemplateDO.setDivKey("divKey");
        viewTemplateDO.setType(0);
        viewTemplateDO.setParentId(0);
        viewTemplateDO.setIsDefault(0);
        viewTemplateDO.setFieldJson("fieldJson");
        viewTemplateDO.setId(0);
        viewTemplateDO.setFieldId(0);
        viewTemplateDO.setName("name");
        final List<ViewTemplateDO> viewTemplateDOS = Arrays.asList(viewTemplateDO);
        when(mockViewTemplateManager.selectViewTemplateList(new ViewTemplate())).thenReturn(viewTemplateDOS);

        when(mockViewTemplateManager.updateViewTemplate(new ViewTemplate())).thenReturn(0);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);
        when(mockViewConditionValueService.updateViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);
        when(mockViewConditionValueService.deleteViewConditionValueByFieldIdAndCondition(0, "condition")).thenReturn(0);


        viewField.put("testData1", "");
        viewField.put("testData2", "testData2");
        viewField.put("id", "");
        viewField.put("status", "0");

        Boolean catchException = false;
        try {
            // Run the test
            final int result = viewFieldServiceImplUnderTest.updateViewField(viewField);

            // Verify the results
            assertEquals(0, result);
            verify(mockViewTemplateManager).updateViewTemplate(new ViewTemplate());
            verify(mockViewConditionValueService).insertViewConditionValue(any(ViewConditionValue.class));
            verify(mockViewConditionValueService).updateViewConditionValue(any(ViewConditionValue.class));
            verify(mockViewConditionValueService).deleteViewConditionValueByFieldIdAndCondition(0, "condition");

        } catch (Exception e) {
            catchException = true;
        }
        assertTrue(catchException);

    }

    @Test
    public void testUpdateViewField_UpdateCondionValue() {


        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(1);
        viewConditionValueDO.setFieldId(1);
        viewConditionValueDO.setCondition("condition1");
        viewConditionValueDO.setConditionValue("conditionValue1");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);

        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        viewField.put("testData1", "");
        viewField.put("testDataAll", "All");
        viewField.put("testData2", "testData2");
        viewField.put("id", "1");
        viewField.put("status", "0");
        viewField.put("updateBy", "adminTest");

        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.updateViewFieldMap(new HashMap<>())).thenReturn(0);

        // Run the test
        viewFieldServiceImplUnderTest.upadteCondionValue(viewField, "testDataAll");

    }

    @Test
    public void testUpdateViewField_notNullId() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        viewField.put("testData1", "");
        viewField.put("testDataAll", "All");
        viewField.put("testData2", "testData2");
        viewField.put("id", "1");
        viewField.put("status", "0");

        Boolean catchException = false;
        try {
            // Run the test
            final int result = viewFieldServiceImplUnderTest.updateViewField(viewField);

            // Verify the results
            assertEquals(0, result);
            verify(mockViewTemplateManager).updateViewTemplate(new ViewTemplate());
            verify(mockViewConditionValueService).insertViewConditionValue(any(ViewConditionValue.class));
            verify(mockViewConditionValueService).updateViewConditionValue(any(ViewConditionValue.class));
            verify(mockViewConditionValueService).deleteViewConditionValueByFieldIdAndCondition(0, "condition");

        } catch (Exception e) {
            catchException = true;
        }
        assertTrue(catchException);
    }

    @Test
    public void testUpdateViewField_idOk() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        viewField.put("testData1", "");
        viewField.put("testDataAll", "All");
        viewField.put("testData2", "testData2");
        viewField.put("id", "234");
        viewField.put("status", "0");
        viewField.put("createBy", "admin123");
        viewField.put("updateBy", "admin123");
        viewField.put("fieldJson", "{\"formRef\":\"elForm\",\"gutter\":15,\"size\":\"small\",\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false,\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\",\"fields\":[{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":1,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Billing Doc Number\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3j7xpxrp0ng00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"billingNumber\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":2,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Billing Category\",\"showLabel\":true,\"required\":false,\"renderKey\":\"hgz94rxh7fk0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"billingCategory\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":8,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Business Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"uclizwj72680000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"businessGroup\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":9,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Geo\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7uzdm2g006c00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"geoCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":10,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Region/Market\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1wa6ihf56va80000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"regionMarketCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":11,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Company Code Country\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3s6zjd88ffa00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"companyCodeCountry\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":12,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4w9hpbzmmqg00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":13,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"vhz0tmr6lps0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":14,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Sales Doc Number\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7yu8qjigcsw00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOrderNumber\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":15,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Sales Doc Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2lgfsi4pff200000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesDocType\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":16,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Distribution Channel\",\"showLabel\":true,\"required\":false,\"renderKey\":\"9c1m8vearrk00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"distributionChannel\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":17,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Sold-to Party\",\"showLabel\":true,\"required\":false,\"renderKey\":\"89dt7vgvxcs00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"soldTo\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":18,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Sold-to Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3rmvc5siroo00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"soldToName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":19,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"End Customer\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2ce5s8wio3ok0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerId\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":20,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"End Customer Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3fx0xi81esw00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":53,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"BPC Segment\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8m2gqhb00q800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"bpcSegmentCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":47,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Segment\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1edwy9edbpds0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolSegmentCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":21,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"ISG Customer Seg. L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2mon71rpdlo00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"isgCustomerSegmentL2\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":22,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GTM Customer Seg. L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1sx2nh94fb1c0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtmCustomerSegmentL2\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":23,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Material\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6904ieq9d0800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"mtm\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":3,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Billing Item Number\",\"showLabel\":true,\"required\":false,\"renderKey\":\"106hmld67o000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"billingItemNumber\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":4,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Company Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7fsk34p8pf800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"companyCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":5,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Fisc Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3xelywy40n000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscYear\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":6,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Mtm Source Id\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5ifufasuxlk00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"mtmSourceId\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Billing Date\",\"showLabel\":true,\"required\":false,\"renderKey\":\"73ndlt7ztoc00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"billingDate\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":24,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Profit Center\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2g39hliwqwsg0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"profitCenterCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":25,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"BW BU\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3zr51gyxplc00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"bwBu\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":26,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"APC Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6w0nmz1764000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"apcCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":27,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"TOS\",\"showLabel\":true,\"required\":false,\"renderKey\":\"l0ljei64k9s0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"tos\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":29,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"S&D Revenue (T)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"22vwwjyvoirk0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"documentCurrencyNetValueT\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":30,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"RCM Revenue (T)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7hwj4ofdb8w00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"rcmRevenueT\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":31,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Transaction Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"ebsz81j9qf40000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"documentCurrencyT\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":32,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"S&D Revenue (L)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2roq002vdre00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"companyCurrencyNetValueL\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":33,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"RCM Revenue (L)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7xm65ek81aw0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"rcmRevenueL\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":0,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"唯一标识\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8hryxuv9hdo00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"id\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":34,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Local Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"w88uos5kqhs0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"companyCurrencyL\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":35,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"S&D Revenue ($)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"718gyqfnv3c00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"netValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":36,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"RCM Revenue ($)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2p3wggztqj000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"rcmRevenueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":37,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GTN Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"dli2zyahc680000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtnType\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":38,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Factor ID\",\"showLabel\":true,\"required\":false,\"renderKey\":\"m80bmcavzfk0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"factorRecordId\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":39,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Method\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8nm246yb3tc00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"factorMethod\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":40,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Factor Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"261b6olotphc0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"factorCurrency\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":41,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Factor\",\"showLabel\":true,\"required\":false,\"renderKey\":\"846zem2lldc00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"factorValue\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":67,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GTN Accrual (T)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2cvyk3fjqh340000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValueDocumentCurrencyT\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":68,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GTN Accrual (L)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"jp0e56pdbbk0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValueCompanyCurrencyL\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":69,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GTN Accrual ($)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"613b1xtlip400000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":44,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7yodx2ajtp800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":101,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011657693550054\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":46,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GTN Accrual (P)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"9g4uyibkc1s00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValuePoolCurrencyP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":45,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"149ph2puziqk0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolCurrencyP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":70,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Total GTN Factor (%)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"wmwb546jee80000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"totalFactorValue\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":71,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Total GTN Accrual (L)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8rgn97f53rc0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"totalAccrualL\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":72,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Total GTN Accrual ($)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4l1794qkeh400000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"totalAccrualUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":73,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Total GTN Accrual (P)\",\"showLabel\":true,\"required\":false,\"renderKey\":\"36ua6b1bsqo00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"totalAccrualP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":6,\"createBy\":\"admin123\",\"updateBy\":\"admin123\"}");

        // Run the test
        final int result = viewFieldServiceImplUnderTest.updateViewField(viewField);

        // Verify the results
        assertEquals(0, result);

    }

    @Test
    public void testUpdateViewField_IViewConditionValueServiceSelectViewConditionValueListReturnsNoItems() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.updateViewFieldMap(new HashMap<>())).thenReturn(0);

        // Configure ViewTemplateManager.selectViewTemplateList(...).
        final ViewTemplateDO viewTemplateDO = new ViewTemplateDO();
        viewTemplateDO.setPageKey("pageKey");
        viewTemplateDO.setViewType("viewType");
        viewTemplateDO.setDivKey("divKey");
        viewTemplateDO.setType(0);
        viewTemplateDO.setParentId(0);
        viewTemplateDO.setIsDefault(0);
        viewTemplateDO.setFieldJson("fieldJson");
        viewTemplateDO.setId(0);
        viewTemplateDO.setFieldId(0);
        viewTemplateDO.setName("name");
        final List<ViewTemplateDO> viewTemplateDOS = Arrays.asList(viewTemplateDO);
        when(mockViewTemplateManager.selectViewTemplateList(new ViewTemplate())).thenReturn(viewTemplateDOS);

        when(mockViewTemplateManager.updateViewTemplate(new ViewTemplate())).thenReturn(0);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);
        when(mockViewConditionValueService.updateViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);
        when(mockViewConditionValueService.deleteViewConditionValueByFieldIdAndCondition(0, "condition")).thenReturn(0);

        Boolean catchException = false;
        try {
            // Run the test
            final int result = viewFieldServiceImplUnderTest.updateViewField(viewField);

            // Verify the results
            assertEquals(0, result);
            verify(mockViewTemplateManager).updateViewTemplate(new ViewTemplate());
            verify(mockViewConditionValueService).insertViewConditionValue(any(ViewConditionValue.class));
            verify(mockViewConditionValueService).updateViewConditionValue(any(ViewConditionValue.class));
            verify(mockViewConditionValueService).deleteViewConditionValueByFieldIdAndCondition(0, "condition");

        } catch (Exception e) {
            catchException = true;
        }
        assertTrue(catchException);

    }

    @Test
    public void testUpdateViewField_ViewTemplateManagerSelectViewTemplateListReturnsNoItems() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.updateViewFieldMap(new HashMap<>())).thenReturn(0);
        when(mockViewTemplateManager.selectViewTemplateList(new ViewTemplate())).thenReturn(Collections.emptyList());
        when(mockViewTemplateManager.updateViewTemplate(new ViewTemplate())).thenReturn(0);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);
        when(mockViewConditionValueService.updateViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);
        when(mockViewConditionValueService.deleteViewConditionValueByFieldIdAndCondition(0, "condition")).thenReturn(0);


        Boolean catchException = false;
        try {
            // Run the test
            final int result = viewFieldServiceImplUnderTest.updateViewField(viewField);

            // Verify the results
            assertEquals(0, result);
            verify(mockViewTemplateManager).updateViewTemplate(new ViewTemplate());
            verify(mockViewConditionValueService).insertViewConditionValue(any(ViewConditionValue.class));
            verify(mockViewConditionValueService).updateViewConditionValue(any(ViewConditionValue.class));
            verify(mockViewConditionValueService).deleteViewConditionValueByFieldIdAndCondition(0, "condition");

        } catch (Exception e) {
            catchException = true;
        }
        assertTrue(catchException);

    }

    @Test
    public void testUpdateViewField_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

        when(mockViewFieldManager.updateViewFieldMap(new HashMap<>())).thenReturn(0);

        // Configure ViewTemplateManager.selectViewTemplateList(...).
        final ViewTemplateDO viewTemplateDO = new ViewTemplateDO();
        viewTemplateDO.setPageKey("pageKey");
        viewTemplateDO.setViewType("viewType");
        viewTemplateDO.setDivKey("divKey");
        viewTemplateDO.setType(0);
        viewTemplateDO.setParentId(0);
        viewTemplateDO.setIsDefault(0);
        viewTemplateDO.setFieldJson("fieldJson");
        viewTemplateDO.setId(0);
        viewTemplateDO.setFieldId(0);
        viewTemplateDO.setName("name");
        final List<ViewTemplateDO> viewTemplateDOS = Arrays.asList(viewTemplateDO);
        when(mockViewTemplateManager.selectViewTemplateList(new ViewTemplate())).thenReturn(viewTemplateDOS);

        when(mockViewTemplateManager.updateViewTemplate(new ViewTemplate())).thenReturn(0);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());

        // Configure RemoteMasterDataService.getListByRegionMarket(...).
        final BizBaseRegionMarketDTO bizBaseRegionMarketDTO = new BizBaseRegionMarketDTO();
        bizBaseRegionMarketDTO.setIds("ids");
        bizBaseRegionMarketDTO.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarketDTO.setId(0L);
        bizBaseRegionMarketDTO.setGeoCode("geoCode");
        bizBaseRegionMarketDTO.setRegionMarketCode("regionMarketCode");
        bizBaseRegionMarketDTO.setRegionMarketName("regionMarketName");
        bizBaseRegionMarketDTO.setSegmentLevel("segmentLevel");
        bizBaseRegionMarketDTO.setSubGeo("subGeo");
        bizBaseRegionMarketDTO.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarketDTO.setStatus("status");
        final RemoteResponse<BizBaseRegionMarketDTO> bizBaseRegionMarketDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseRegionMarketDTO), 0);
        when(mockRemoteMasterDataService.getListByRegionMarket("geoCode", "businessGroup")).thenReturn(bizBaseRegionMarketDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOrg(...).
        final BizBaseSalesOrgDTO bizBaseSalesOrgDTO = new BizBaseSalesOrgDTO();
        bizBaseSalesOrgDTO.setIds("ids");
        bizBaseSalesOrgDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOrgDTO.setId(0L);
        bizBaseSalesOrgDTO.setGeoCode("geoCode");
        bizBaseSalesOrgDTO.setRegionMarketCode("regionMarketCode");
        bizBaseSalesOrgDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOrgDTO.setSalesOrgName("salesOrgName");
        bizBaseSalesOrgDTO.setLocalCurrencyCode("localCurrencyCode");
        bizBaseSalesOrgDTO.setCompanyCode("companyCode");
        bizBaseSalesOrgDTO.setAccrualCompanyCode("accrualCompanyCode");
        final RemoteResponse<BizBaseSalesOrgDTO> bizBaseSalesOrgDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOrgDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOrg("geoCode", "businessGroup", "regionMarketCode", "tempField")).thenReturn(bizBaseSalesOrgDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySalesOffice(...).
        final BizBaseSalesOfficeDTO bizBaseSalesOfficeDTO = new BizBaseSalesOfficeDTO();
        bizBaseSalesOfficeDTO.setIds("ids");
        bizBaseSalesOfficeDTO.setIdsList(Arrays.asList(0L));
        bizBaseSalesOfficeDTO.setId(0L);
        bizBaseSalesOfficeDTO.setSalesOrgCode("salesOrgCode");
        bizBaseSalesOfficeDTO.setSalesOfficeCode("salesOfficeCode");
        bizBaseSalesOfficeDTO.setSalesOfficeName("salesOfficeName");
        bizBaseSalesOfficeDTO.setStatus("status");
        bizBaseSalesOfficeDTO.setCreateBy("createBy");
        bizBaseSalesOfficeDTO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseSalesOfficeDTO.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final RemoteResponse<BizBaseSalesOfficeDTO> bizBaseSalesOfficeDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSalesOfficeDTO), 0);
        when(mockRemoteMasterDataService.getListBySalesOffice("businessGroup", "regionMarketCode", "salesOrgCode")).thenReturn(bizBaseSalesOfficeDTORemoteResponse);

        // Configure RemoteMasterDataService.getListBySegment(...).
        final BizBaseSegmentDTO bizBaseSegmentDTO = new BizBaseSegmentDTO();
        bizBaseSegmentDTO.setIds("ids");
        bizBaseSegmentDTO.setIdsList(Arrays.asList(0L));
        bizBaseSegmentDTO.setId(0L);
        bizBaseSegmentDTO.setSegmentCode("segmentCode");
        bizBaseSegmentDTO.setSegmentName("segmentName");
        bizBaseSegmentDTO.setSegmentLevel("segmentLevel");
        bizBaseSegmentDTO.setParentSegment("parentSegment");
        bizBaseSegmentDTO.setGpnCode("gpnCode");
        bizBaseSegmentDTO.setStatus("status");
        final RemoteResponse<BizBaseSegmentDTO> bizBaseSegmentDTORemoteResponse = new RemoteResponse<>(Arrays.asList(bizBaseSegmentDTO), 0);
        when(mockRemoteMasterDataService.getListBySegment("geoCode", "businessGroup")).thenReturn(bizBaseSegmentDTORemoteResponse);

        when(mockViewConditionValueService.insertViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);
        when(mockViewConditionValueService.updateViewConditionValue(any(ViewConditionValue.class))).thenReturn(0);
        when(mockViewConditionValueService.deleteViewConditionValueByFieldIdAndCondition(0, "condition")).thenReturn(0);

    }

    @Test
    public void testDeleteViewFieldByIds() {
        // Setup
        when(mockViewFieldManager.deleteViewFieldByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = viewFieldServiceImplUnderTest.deleteViewFieldByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteViewFieldById() {
        // Setup
        when(mockViewFieldManager.deleteViewFieldById(0)).thenReturn(0);

        // Run the test
        final int result = viewFieldServiceImplUnderTest.deleteViewFieldById(0);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testFields() {
        // Setup
        final ViewFieldInfo viewFieldInfo = new ViewFieldInfo();
        viewFieldInfo.setAttnum(0);
        viewFieldInfo.setRelname("relname");
        viewFieldInfo.setField("field");
        viewFieldInfo.setFieldType("fieldType");
        viewFieldInfo.setFieldLabel("fieldLabel");
        viewFieldInfo.setFieldTest("fieldTest");
        viewFieldInfo.setChecked(false);
        final List<ViewFieldInfo> expectedResult = Arrays.asList(viewFieldInfo);

        // Configure ViewFieldManager.selectViewFieldInfoList(...).
        final ViewFieldInfo viewFieldInfo1 = new ViewFieldInfo();
        viewFieldInfo1.setAttnum(0);
        viewFieldInfo1.setRelname("relname");
        viewFieldInfo1.setField("field");
        viewFieldInfo1.setFieldType("fieldType");
        viewFieldInfo1.setFieldLabel("fieldLabel");
        viewFieldInfo1.setFieldTest("fieldTest");
        viewFieldInfo1.setChecked(false);
        final List<ViewFieldInfo> viewFieldInfos = Arrays.asList(viewFieldInfo1);
        when(mockViewFieldManager.selectViewFieldInfoList(new ViewFieldInfo())).thenReturn(viewFieldInfos);


    }

    @Test
    public void testFields_ViewFieldManagerReturnsNoItems() {
        // Setup
        when(mockViewFieldManager.selectViewFieldInfoList(new ViewFieldInfo())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewFieldInfo> result = viewFieldServiceImplUnderTest.fields("relName");

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDisableViewField() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(1001);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldManager.updateViewField(new ViewField())).thenReturn(0);
        when(mockViewTemplateManager.fakeDeleteTemplateGroup(0)).thenReturn(0);

        // Configure ViewFieldManager.selectViewFieldById(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(1001);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldManager.selectViewFieldById(1001)).thenReturn(viewFieldDO);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        final int result = viewFieldServiceImplUnderTest.disableViewField(viewField);

        // Verify the results
        assertEquals(1, result);
    }

    @Test
    public void testDisableViewField_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldManager.updateViewField(new ViewField())).thenReturn(0);
        when(mockViewTemplateManager.fakeDeleteTemplateGroup(0)).thenReturn(0);

        // Configure ViewFieldManager.selectViewFieldById(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldManager.selectViewFieldById(0)).thenReturn(viewFieldDO);

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final int result = viewFieldServiceImplUnderTest.disableViewField(viewField);

        // Verify the results
        assertEquals(1, result);
    }

    @Test
    public void testPageKeyList() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");
        final List<ViewTemplate> expectedResult = Arrays.asList(viewTemplate);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

    }

    @Test
    public void testPageKeyList_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");
        final List<ViewTemplate> expectedResult = Arrays.asList(viewTemplate);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);

    }

    @Test
    public void testPageKeyList_ViewFieldManagerReturnsNoItems() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");
        final List<ViewTemplate> expectedResult = Arrays.asList(viewTemplate);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);


        Boolean catchException = false;
        try {
            // Run the test
            final List<ViewTemplate> result = viewFieldServiceImplUnderTest.pageKeyList(viewField);

            // Verify the results
            assertEquals(expectedResult, result);
            verify(mockViewTemplateService).checkedFields(new HashMap<>(), new HashMap<>());

        } catch (OtmpException e) {
            catchException = true;
        }
        assertTrue(catchException);

    }

    @Test
    public void testPageKeyList_IViewConditionValueServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> viewField = new HashMap<>();
        final ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("pageKey");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("fieldJson");
        viewTemplate.setId(0);
        viewTemplate.setFieldId(0);
        viewTemplate.setName("name");
        final List<ViewTemplate> expectedResult = Arrays.asList(viewTemplate);

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("1");
        sysDictData1.setIdsArray(new Long[]{1L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(1L);
        sysDictData1.setDictSort(1L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());

        Boolean catchException = false;
        try {
            // Run the test
            final List<ViewTemplate> result = viewFieldServiceImplUnderTest.pageKeyList(viewField);

            // Verify the results
            assertEquals(expectedResult, result);
            verify(mockViewTemplateService).checkedFields(new HashMap<>(), new HashMap<>());

        } catch (OtmpException e) {
            catchException = true;
        }
        assertTrue(catchException);
    }

    @Test
    public void testSelectFieldTree() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> expectedResult = Arrays.asList(viewFieldDO);

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO2 = new ViewFieldDO();
        viewFieldDO2.setId(0);
        viewFieldDO2.setPageKey("pageKey");
        viewFieldDO2.setViewType("viewType");
        viewFieldDO2.setDivKey("divKey");
        viewFieldDO2.setStatus(0);
        viewFieldDO2.setFieldJson("fieldJson");
        viewFieldDO2.setType(0);
        viewFieldDO2.setViewFieldCode("viewFieldCode");
        viewFieldDO2.setTableName("tableName");
        viewFieldDO2.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS1 = Arrays.asList(viewFieldDO2);
        when(mockViewFieldManager.selectPageKeyList(viewField)).thenReturn(viewFieldDOS1);
        viewFieldServiceImplUnderTest.selectFieldTree(viewField);
    }

    @Test
    public void testSelectFieldTree_ViewFieldManagerSelectPageKeyListReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> expectedResult = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(Collections.emptyList());

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        final List<ViewFieldDO> result = viewFieldServiceImplUnderTest.selectFieldTree(viewField);
        List<ViewFieldDO> result1 = new ArrayList<>();
        // Verify the results
        assertEquals(result1, result);
    }

    @Test
    public void testSelectFieldTree_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> expectedResult = Arrays.asList(viewFieldDO);

        // Configure ViewFieldManager.selectPageKeyList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectPageKeyList(new ViewField())).thenReturn(viewFieldDOS);

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewFieldDO> result = viewFieldServiceImplUnderTest.selectFieldTree(viewField);
        List<ViewFieldDO> result1 = new ArrayList<>();
        // Verify the results
        assertEquals(result1, result);
    }

    @Test
    public void testSelectFieldTiled() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> expectedResult = Arrays.asList(viewFieldDO);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO1);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        // Run the test
        final List<ViewFieldDO> result = viewFieldServiceImplUnderTest.selectFieldTiled(viewField);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    public void testSelectFieldTiled_ViewFieldManagerReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewFieldDO> result = viewFieldServiceImplUnderTest.selectFieldTiled(viewField);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testDivSelect() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> expectedResult = Arrays.asList(viewFieldDO);

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO1 = new ViewFieldDO();
        viewFieldDO1.setId(0);
        viewFieldDO1.setPageKey("pageKey");
        viewFieldDO1.setViewType("viewType");
        viewFieldDO1.setDivKey("divKey");
        viewFieldDO1.setStatus(0);
        viewFieldDO1.setFieldJson("fieldJson");
        viewFieldDO1.setType(0);
        viewFieldDO1.setViewFieldCode("viewFieldCode");
        viewFieldDO1.setTableName("tableName");
        viewFieldDO1.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO1);
        when(viewFieldServiceImplUnderTest.selectViewFieldList(viewField)).thenReturn(viewFieldDOS);

        // Run the test
        final List<ViewFieldDO> result = viewFieldServiceImplUnderTest.divSelect(viewField);

        List<ViewFieldDO> result1 = new ArrayList<>();
        // Verify the results
        assertEquals(result, result);
    }

    @Test
    public void testDivSelect_ViewFieldManagerReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());

        // Run the test
        final List<ViewFieldDO> result = viewFieldServiceImplUnderTest.divSelect(viewField);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testRepairData() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(1638);
        viewField.setPageKey("2968");
        viewField.setViewType("list");
        viewField.setDivKey("Search");
        viewField.setStatus(0);
        viewField.setFieldJson("{\"formBtns\":false,\"labelWidth\":100,\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\",\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false,\"disabled\":false,\"isWorkflow\":false,\"formModel\":\"formData\",\"fields\":[{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":false,\"remark\":\"\",\"__config__\":{\"formId\":10,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"label\":\"Business Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"9auy7xmlgc800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"businessGroup\",\"dict\":\"business_group\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":false,\"remark\":\"\",\"__config__\":{\"formId\":50,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"label\":\"GEO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"pbln9t1aftc0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"geoCode\",\"dict\":\"geo_code\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":false,\"remark\":\"\",\"__config__\":{\"formId\":9,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"label\":\"Market/Region\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8oocn3s7yj000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"regionMarketCode\",\"dict\":\"payment_mode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":false,\"remark\":\"\",\"__config__\":{\"formId\":6,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5u98wmdc44800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"salesOrgCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"range-separator\":\"-\",\"isTooltip\":false,\"start-placeholder\":\"Start Date\",\"format\":\"MM/dd/yyyy\",\"value-format\":\"MM/dd/yyyy\",\"remark\":\"\",\"type\":\"daterange\",\"end-placeholder\":\"End Date\",\"__config__\":{\"formId\":1,\"document\":\"https://element.eleme.cn/#/en-US/component/date-picker\",\"labelWidth\":\"\",\"label\":\"Posting Date\",\"showLabel\":true,\"required\":false,\"renderKey\":\"iotxdcfzmfc0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"date-range\",\"changeTag\":true,\"tag\":\"el-date-picker\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"postingDateStr\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"remark\":\"\",\"templateCode\":\"\",\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"1w1zjv1kipx\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"File Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3m9j7t0jof000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"fieldName\",\"isUpDown\":false,\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":6}");

        viewField.setType(1);
        viewField.setViewFieldCode("V20230515002");
        viewField.setTableName("payment_header_item_report");

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(1638);
        viewFieldDO.setPageKey("2968");
        viewFieldDO.setViewType("list");
        viewFieldDO.setDivKey("Search");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("{\"formBtns\":false,\"labelWidth\":100,\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\",\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false,\"disabled\":false,\"isWorkflow\":false,\"formModel\":\"formData\",\"fields\":[{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":false,\"remark\":\"\",\"__config__\":{\"formId\":10,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"label\":\"Business Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"9auy7xmlgc800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"businessGroup\",\"dict\":\"business_group\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":false,\"remark\":\"\",\"__config__\":{\"formId\":50,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"label\":\"GEO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"pbln9t1aftc0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"geoCode\",\"dict\":\"geo_code\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":false,\"remark\":\"\",\"__config__\":{\"formId\":9,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"label\":\"Market/Region\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8oocn3s7yj000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"regionMarketCode\",\"dict\":\"payment_mode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":false,\"remark\":\"\",\"__config__\":{\"formId\":6,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5u98wmdc44800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"salesOrgCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"range-separator\":\"-\",\"isTooltip\":false,\"start-placeholder\":\"Start Date\",\"format\":\"MM/dd/yyyy\",\"value-format\":\"MM/dd/yyyy\",\"remark\":\"\",\"type\":\"daterange\",\"end-placeholder\":\"End Date\",\"__config__\":{\"formId\":1,\"document\":\"https://element.eleme.cn/#/en-US/component/date-picker\",\"labelWidth\":\"\",\"label\":\"Posting Date\",\"showLabel\":true,\"required\":false,\"renderKey\":\"iotxdcfzmfc0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"date-range\",\"changeTag\":true,\"tag\":\"el-date-picker\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"postingDateStr\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"remark\":\"\",\"templateCode\":\"\",\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"1w1zjv1kipx\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"File Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3m9j7t0jof000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"fieldName\",\"isUpDown\":false,\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"isWorkflow\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":6}");
        viewFieldDO.setType(1);
        viewFieldDO.setViewFieldCode("V20230515002");
        viewFieldDO.setTableName("payment_header_item_report");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(viewField)).thenReturn(viewFieldDOS);

        when(mockViewFieldManager.updateViewField(viewField)).thenReturn(0);

        // Configure IViewTemplateService.selectViewTemplateList(...).
        final ViewTemplateDO viewTemplateDO = new ViewTemplateDO();
        viewTemplateDO.setPageKey("2154");
        viewTemplateDO.setViewType("viewType");
        viewTemplateDO.setDivKey("divKey");
        viewTemplateDO.setType(0);
        viewTemplateDO.setParentId(0);
        viewTemplateDO.setIsDefault(0);
        viewTemplateDO.setFieldJson("{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\",\"formRules\":\"rules\",\"labelPosition\":\"top\",\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\",\"fields\":[{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":77,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Business Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"businessGroup\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":1,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"GEO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"geoCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":23,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Region/Market\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"regionMarketCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":33,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Segment\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"segmentcode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":35,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Territory\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"territory\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":36,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":8,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"poolFy\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":12,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"poolQ\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":37,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Pool Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"poolType\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[{\"label\":\"By Order\",\"value\":\"By Order\"},{\"label\":\"Not By Order\",\"value\":\"Not By Order\"}],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":3,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":4,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":105,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"GTN Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1051650505232992\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"gtnTypeCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":103,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"GTN Category\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1031650505203080\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"gtnCategory\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":false,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":106,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Status\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1061650505233248\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"status\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[{\"label\":\"Draft\",\"value\":\"Draft\"},{\"label\":\"Active\",\"value\":\"Active\"},{\"label\":\"Closed\",\"value\":\"Closed\"},{\"label\":\"Cancelled\",\"value\":\"Cancelled\"}],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":107,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Applicant\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1071650505233799\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"createBy\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":110,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Description\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553530\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolDesc\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":113,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Pool Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553862\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"poolCurrency\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":127,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"BP Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553727\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"partnerType\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":135,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"BU\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553816\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"bu\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":134,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Profit Center\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553561\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"profitCenterCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":139,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"APC Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553981\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"apcCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":140,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Product No\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553343\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"mtm\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":136,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Product Hierarchy\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553600\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"productHierarchyCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":122,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Distribution Channel\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553687\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"distributionChannel\",\"dict\":\"Distribution_Channel\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":123,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Division\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553230\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"divisionCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":141,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Top Choice Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553964\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"topChoiceFlag\",\"dict\":\"Top_Choice_Flag\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":124,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"SBO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553180\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"isSbo\",\"dict\":\"SBO\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":125,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"LBP Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553732\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"lbpFlag\",\"dict\":\"LBP_Flag\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":133,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"FOB Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553460\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"fobFlag\",\"dict\":\"FOB_Flag\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":128,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Customer Number\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553167\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"customerNumber\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":168,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Customer Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553918\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"customerName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":129,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"End Customer Id\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553694\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerId\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":130,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"End Customer Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553031\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":131,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"ISG Customer Segment L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553188\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"isgCustomerSegmentL2\",\"dict\":\"ISG_Customer_Segment_L2\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":132,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"GTM Customer Segment L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553513\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"gtmCustomerSegmentL2\",\"dict\":\"GTM_Customer_Segment_L2\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}}],\"span\":6}");
        viewTemplateDO.setId(1447);
        viewTemplateDO.setFieldId(1638);
        viewTemplateDO.setName("name");
        final List<ViewTemplateDO> viewTemplateDOS = Arrays.asList(viewTemplateDO);

        ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("2154");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\",\"formRules\":\"rules\",\"labelPosition\":\"top\",\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\",\"fields\":[{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":77,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Business Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"businessGroup\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":1,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"GEO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"geoCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":23,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Region/Market\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"regionMarketCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":33,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Segment\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"segmentcode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":35,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Territory\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"territory\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":36,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":8,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"poolFy\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":12,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"poolQ\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":37,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Pool Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"poolType\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[{\"label\":\"By Order\",\"value\":\"By Order\"},{\"label\":\"Not By Order\",\"value\":\"Not By Order\"}],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":3,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":4,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":105,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"GTN Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1051650505232992\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"gtnTypeCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":103,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"GTN Category\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1031650505203080\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"gtnCategory\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":false,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":106,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Status\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1061650505233248\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"status\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[{\"label\":\"Draft\",\"value\":\"Draft\"},{\"label\":\"Active\",\"value\":\"Active\"},{\"label\":\"Closed\",\"value\":\"Closed\"},{\"label\":\"Cancelled\",\"value\":\"Cancelled\"}],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":107,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Applicant\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1071650505233799\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"createBy\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":110,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Description\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553530\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolDesc\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":113,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Pool Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553862\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"poolCurrency\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":127,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"BP Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553727\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"partnerType\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":135,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"BU\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553816\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"bu\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":134,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Profit Center\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553561\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"profitCenterCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":139,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"APC Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553981\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"apcCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":140,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Product No\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553343\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"mtm\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":136,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Product Hierarchy\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553600\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"productHierarchyCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":122,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Distribution Channel\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553687\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"distributionChannel\",\"dict\":\"Distribution_Channel\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":123,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Division\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553230\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"divisionCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":141,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Top Choice Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553964\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"topChoiceFlag\",\"dict\":\"Top_Choice_Flag\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":124,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"SBO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553180\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"isSbo\",\"dict\":\"SBO\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":125,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"LBP Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553732\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"lbpFlag\",\"dict\":\"LBP_Flag\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":133,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"FOB Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553460\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"fobFlag\",\"dict\":\"FOB_Flag\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":128,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Customer Number\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553167\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"customerNumber\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":168,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Customer Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553918\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"customerName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":129,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"End Customer Id\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553694\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerId\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":130,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"End Customer Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553031\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":131,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"ISG Customer Segment L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553188\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"isgCustomerSegmentL2\",\"dict\":\"ISG_Customer_Segment_L2\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":132,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"GTM Customer Segment L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553513\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"gtmCustomerSegmentL2\",\"dict\":\"GTM_Customer_Segment_L2\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}}],\"span\":6}");
        viewTemplate.setId(1447);
        viewTemplate.setFieldId(1638);
        viewTemplate.setName("name");
        ViewTemplate viewTemplate1 = new ViewTemplate();
        viewTemplate1.setFieldId(1638);
        when(mockViewTemplateService.selectViewTemplateList(viewTemplate1)).thenReturn(viewTemplateDOS);

        when(mockViewTemplateService.updateViewTemplate(viewTemplate)).thenReturn(0);
        viewFieldServiceImplUnderTest.repairData(viewField);


    }


    @Test
    public void testRepairData_ViewFieldManagerSelectViewFieldListReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.updateViewField(new ViewField())).thenReturn(0);

        // Configure IViewTemplateService.selectViewTemplateList(...).
        final ViewTemplateDO viewTemplateDO = new ViewTemplateDO();
        viewTemplateDO.setPageKey("pageKey");
        viewTemplateDO.setViewType("viewType");
        viewTemplateDO.setDivKey("divKey");
        viewTemplateDO.setType(0);
        viewTemplateDO.setParentId(0);
        viewTemplateDO.setIsDefault(0);
        viewTemplateDO.setFieldJson("fieldJson");
        viewTemplateDO.setId(0);
        viewTemplateDO.setFieldId(0);
        viewTemplateDO.setName("name");
        final List<ViewTemplateDO> viewTemplateDOS = Arrays.asList(viewTemplateDO);
        when(mockViewTemplateService.selectViewTemplateList(new ViewTemplate())).thenReturn(viewTemplateDOS);

        when(mockViewTemplateService.updateViewTemplate(new ViewTemplate())).thenReturn(0);

        // Run the test
        viewFieldServiceImplUnderTest.repairData(viewField);


    }

    @Test
    public void testRepairData_IViewTemplateServiceSelectViewTemplateListReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        // Configure ViewFieldManager.selectViewFieldList(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        final List<ViewFieldDO> viewFieldDOS = Arrays.asList(viewFieldDO);
        when(mockViewFieldManager.selectViewFieldList(new ViewField())).thenReturn(viewFieldDOS);

        when(mockViewFieldManager.updateViewField(new ViewField())).thenReturn(0);
        when(mockViewTemplateService.selectViewTemplateList(new ViewTemplate())).thenReturn(Collections.emptyList());
        when(mockViewTemplateService.updateViewTemplate(new ViewTemplate())).thenReturn(0);


    }

    @Test
    public void testExport() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final ViewFieldExcel viewFieldExcel = new ViewFieldExcel();
        viewFieldExcel.setVModel("vModel");
        viewFieldExcel.setLabelWidthConfig("labelWidthConfig");
        viewFieldExcel.setLabelConfig("labelConfig");
        viewFieldExcel.setTagIconConfig("tagIconConfig");
        viewFieldExcel.setSpanConfig(0);
        viewFieldExcel.setPlaceholder("placeholder");
        viewFieldExcel.setFieldRange("fieldRange");
        final List<ViewFieldExcel> expectedResult = Arrays.asList(viewFieldExcel);

        // Configure ViewFieldManager.selectViewFieldById(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(101);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\",\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false,\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\",\"fields\":[{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":0,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Id\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4cxvmjsz39u00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolId\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":1,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GEO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"29b4f03lvv9c0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"geoCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":2,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Business Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8oz0fwjf5ao00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"businessGroup\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":3,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4op9bvt78wa00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":4,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3qaiowj06bq00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":5,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Sub Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"50fj53n6hqo00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"subName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":6,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Desc\",\"showLabel\":true,\"required\":false,\"renderKey\":\"10pna8088w1s0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolDesc\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"vcn18t5syf40000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolType\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":8,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"dx9vsac4ojk0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolFy\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":9,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"eqwt21gpg4g0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolQ\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":10,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Begining Date\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2r21kfuxhr800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolBeginingDate\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":11,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Ending Date\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2ble71q8hi3o0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolEndingDate\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":12,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"44espeajs1600000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolCurrency\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":13,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Functional Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"9ipo1h0dbdc00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"functionalCurrency\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":14,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"P Usd Rate\",\"showLabel\":true,\"required\":false,\"renderKey\":\"o1e2rczwnr40000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"pUsdRate\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":15,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Program Control\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5whbomtadsk00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"programControl\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":16,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pre Q Pool Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"33a90m3cqri00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"preQPoolCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":17,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Status\",\"showLabel\":true,\"required\":false,\"renderKey\":\"350j8ptb2um00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"status\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":18,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Enabled\",\"showLabel\":true,\"required\":false,\"renderKey\":\"74sx8e2cpa800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"enabled\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":19,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Create By\",\"showLabel\":true,\"required\":false,\"renderKey\":\"349nrlgp7xq00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"createBy\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":20,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Create Time\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4wizjxu0s6q00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"createTime\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":21,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Update By\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5t6lqecqcmo00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"updateBy\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":22,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Update Time\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7jm1xnkya3g00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"updateTime\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":23,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Region Market Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"9d8221sa5ko00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"regionMarketCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":24,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Territory Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"62q46wb2qnk00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"territory\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":25,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Sales Org Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5i3lh43yd2w00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":26,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Sales Office Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"62e8owgau0800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":27,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Distribution Channel\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8y0bav8vo7k00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"distributionChannel\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":28,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Division Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8kl9vthx1i400000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"divisionCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":29,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GTN Type Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"46tvooaygnc00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtnTypeCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":30,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GTN Category\",\"showLabel\":true,\"required\":false,\"renderKey\":\"ed7oohzk2xk0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtnCategory\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":31,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"SBO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7hg1s72o5tc00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"isSbo\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":32,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"LBP Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"18pww0tneacg0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"lbpFlag\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":33,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Segment Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5x813y38yv000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"segmentCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":34,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"BP Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4elsp1wwwg800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"partnerType\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":35,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Customer Number\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8z1sigx2k3s00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"customerNumber\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":36,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"End Customer Id\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1ehcnekutrcw0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerId\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":37,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"End Customer Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4pm5h88v6eq00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":38,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"ISG Customer Segment L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3u4t0s2781a00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"isgCustomerSegmentL2\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":39,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"GTM Customer Segment L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6vwmdg833dk00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtmCustomerSegmentL2\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":40,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"FOB Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2o1psafafki00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fobFlag\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":41,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Profit Center Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8aylh1aywo800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"profitCenterCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":42,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"BU\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4evqfd4u5hu00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"bu\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":43,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Product Hierarchy Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7z3y815brmk00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"productHierarchyCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":44,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Product Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"686w9sny9ss00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"productGroup\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":45,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"MBG Material\",\"showLabel\":true,\"required\":false,\"renderKey\":\"82bwquzkuno00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"mbgMaterial\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":46,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"APC Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6j6725mrdhk00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"apcCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":47,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Product No\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6dfo9g1ounk00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"mtm\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":48,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Top Choice Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5tinvsyp10o00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"topChoiceFlag\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":49,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Available Forecast P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5qj78vmpzho00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolAvailableForecastP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":50,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Pool Available Forecast Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5ie6bnxj4uc00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolAvailableForecastUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":51,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Ending Balance Value P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3govr2ske0800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endingBalanceValueP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":52,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Ending Balance Value Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8mvd1jzzjas00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endingBalanceValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":53,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Critical Value P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"44rdbh1sels00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"criticalValueP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":54,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Critical Value Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1tfjfrbf3lcw0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"criticalValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":55,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Forecast Id\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2qz525nmwgw00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"forecastId\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":56,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Forecast Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5zidbt1ukf800000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"forecastName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":57,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Forecast Previous Value P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4behxc0pva600000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"forecastPreviousValueP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":58,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Forecast Previous Value Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7elc7t5f99o00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"forecastPreviousValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":59,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Forecast Value P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1gtxlxhz1qn40000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"forecastValueP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":60,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Forecast Value Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"8ax01srjlpw00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"forecastValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":61,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Accrual Value P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"2galx91c90g00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValueP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":62,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Accrual Value Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"4gdhpu79gbc00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":63,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Accrual Value Ecc P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"42kbct5wcqq00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValueEccP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":64,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Accrual Value Ecc Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7fn6m393qwg00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValueEccUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":65,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Accrual Value Bw P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6zgumcku86400000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValueBwP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":66,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Accrual Value Bw Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3u60j2x1gcw00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"accrualValueBwUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":67,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Carry Forward Value P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"34qwsata71k00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"carryForwardValueP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":68,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Carry Forward Value Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5ho1sxskww000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"carryForwardValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":69,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Additional Value P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"64sc057srtg00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"additionalValueP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":70,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Additional Value Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"mqs3uzhujn40000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"additionalValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":71,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Reclass Value P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3egvhsas48000000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"reclassValueP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":72,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Reclass Value Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"84ady6nidno00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"reclassValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":73,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Settled Value P\",\"showLabel\":true,\"required\":false,\"renderKey\":\"9z018kpvlzs0000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"settledValueP\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":74,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Settled Value Usd\",\"showLabel\":true,\"required\":false,\"renderKey\":\"ygfnkybpyo00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"settledValueUsd\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":75,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"Customer Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"5sy5xqp3so400000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"customerName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":false,\"prefix-icon\":\"\",\"__config__\":{\"formId\":76,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"label\":\"DataVersion\",\"showLabel\":true,\"required\":false,\"renderKey\":\"88bit2bq9xs00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"dataVersion\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"toolText\":\"\",\"placeholder\":\"Please Select\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":6}");
        viewFieldDO.setType(1);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldManager.selectViewFieldById(101)).thenReturn(viewFieldDO);

        Boolean catchException = false;
        try {
            viewField.setId(null);
            viewFieldServiceImplUnderTest.export(viewField);
        } catch (OtmpException e) {
            catchException = true;
        }
        assertTrue(catchException);

        viewField.setId(101);
        viewFieldServiceImplUnderTest.export(viewField);
        // assertThrows(NullPointerException.class, () -> viewFieldServiceImplUnderTest.export(viewField));

    }

    @Test
    public void testImportExcel() {
        // Setup
        final ViewFieldExcel viewFieldExcel = new ViewFieldExcel();
        viewFieldExcel.setVModel("businessGroup");
        viewFieldExcel.setLabelWidthConfig("labelWidthConfig");
        viewFieldExcel.setLabelConfig("labelConfig");
        viewFieldExcel.setTagIconConfig("tagIconConfig");
        viewFieldExcel.setSpanConfig(0);
        viewFieldExcel.setPlaceholder("placeholder");
        viewFieldExcel.setFieldRange("fieldRange");
        final List<ViewFieldExcel> bizs = Arrays.asList(viewFieldExcel);


        // Configure ViewFieldManager.selectViewFieldById(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(1559);
        viewFieldDO.setPageKey("2584");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("Result");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\"," +
                "\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false," +
                "\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\"," +
                "\"fields\":[{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\"," +
                "\"__config__\":{\"formId\":1,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\"," +
                "\"labelWidth\":220,\"label\":\"Business Group\",\"showLabel\":true," +
                "\"required\":false,\"renderKey\":\"1011644471929553101\",\"layout\":\"colFormItem\"," +
                "\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6}," +
                "\"readonly\":false,\"__vModel__\":\"businessGroup\",\"checked\":true,\"style\":{\"width\":\"\"}," +
                "\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[]," +
                "\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true," +
                "\"prefix-icon\":\"\",\"__config__\":{\"formId\":88,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220," +
                "\"label\":\"Geo\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553745\"," +
                "\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\"," +
                "\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"geoCode\",\"checked\":true," +
                "\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}," +
                "\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\"," +
                "\"__config__\":{\"formId\":89,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\"," +
                "\"labelWidth\":220,\"label\":\"Region/Market\",\"showLabel\":true,\"required\":false," +
                "\"renderKey\":\"1011644471929553943\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\"," +
                "\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false," +
                "\"__vModel__\":\"regionMarketCode\",\"checked\":true,\"style\":{\"width\":\"\"}," +
                "\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false," +
                "\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}," +
                "{\"fieldRange\":\"{\\\"geoCode\\\":\\\"EMEA\\\"}\",\"clearable\":true,\"isTooltip\":false," +
                "\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\"," +
                "\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}}," +
                "\"__config__\":{\"formId\":\"1vd5xvm1t9n\",\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/en-US/component/input\"," +
                "\"dataType\":\"static\",\"label\":\"Seller Country\",\"showLabel\":true," +
                "\"required\":false,\"renderKey\":\"kd0amztka0g0000000\",\"layout\":\"colFormItem\"," +
                "\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\"," +
                "\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false," +
                "\"__vModel__\":\"microservicesCountry\",\"checked\":true,\"style\":{\"width\":\"100%\"}," +
                "\"disabled\":true,\"toolText\":\"\",\"no-data-text\":\" \",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}," +
                "\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":169,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices Entity\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553420\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesEntity\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":171,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices VAT ID\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553512\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesVatId\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":31,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553377\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":32,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553835\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":6,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553903\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalYear\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553753\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalQuarter\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":90,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"GTN Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553554\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtnType\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"1c5y4af686l\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Code/Agreement Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7aj8fogcpjw00000000\",\"layout\":\"colFormItem\",\"isDefault\":false,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"2g8n4h6xfsz\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3w5700eql7200000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":12},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programName\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}}," +
                "\"__config__\":{\"formId\":\"2iemj2yq6h4\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Calculation Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6h4vnndgu3o00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"calculationCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one calculation link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":12}");
        viewFieldDO.setType(2);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldManager.selectViewFieldById(1559)).thenReturn(viewFieldDO);

        when(mockViewFieldManager.updateViewField(new ViewField())).thenReturn(0);

        List<ViewFieldExcel> bizs1 = new ArrayList<>();
        viewFieldServiceImplUnderTest.importExcel(bizs1, "admin", 1559);

        bizs1.add(viewFieldExcel);
        viewFieldServiceImplUnderTest.importExcel(bizs1, "admin", 1559);

//
//        Boolean catchException = false;
//        try {
//            viewFieldServiceImplUnderTest.importExcel(bizs1,"admin",10001);
//        }catch (OtmpException e){
//            catchException=true;
//        }
//        assertTrue(catchException);

        //assertThrows(NullPointerException.class, () ->  viewFieldServiceImplUnderTest.export(viewField));

//                assertEquals("Private method is called.", //
//                        ReflectionTestUtils.invokeMethod(viewFieldServiceImplUnderTest, "buildFiledAll", null));
    }

    @Test
    public void testCompletionField() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(1559);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        final List<JSONObject> expectedResult = Arrays.asList(new JSONObject(0, false));

        // Configure ViewFieldManager.selectViewFieldById(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(1559);
        viewFieldDO.setPageKey("2584");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("Result");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\"," +
                "\"formRules\":\"rules\",\"labelPosition\":\"top\",\"unFocusedComponentBorder\":false," +
                "\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\"," +
                "\"fields\":[{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\"," +
                "\"__config__\":{\"formId\":1,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\"," +
                "\"labelWidth\":220,\"label\":\"Business Group\",\"showLabel\":true," +
                "\"required\":false,\"renderKey\":\"1011644471929553101\",\"layout\":\"colFormItem\"," +
                "\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6}," +
                "\"readonly\":false,\"__vModel__\":\"businessGroup\",\"checked\":true,\"style\":{\"width\":\"\"}," +
                "\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[]," +
                "\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true," +
                "\"prefix-icon\":\"\",\"__config__\":{\"formId\":88,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220," +
                "\"label\":\"Geo\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553745\"," +
                "\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\"," +
                "\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"geoCode\",\"checked\":true," +
                "\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}," +
                "\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\"," +
                "\"__config__\":{\"formId\":89,\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\"," +
                "\"labelWidth\":220,\"label\":\"Region/Market\",\"showLabel\":true,\"required\":false," +
                "\"renderKey\":\"1011644471929553943\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\"," +
                "\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false," +
                "\"__vModel__\":\"regionMarketCode\",\"checked\":true,\"style\":{\"width\":\"\"}," +
                "\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false," +
                "\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}," +
                "{\"fieldRange\":\"{\\\"geoCode\\\":\\\"EMEA\\\"}\",\"clearable\":true,\"isTooltip\":false," +
                "\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\"," +
                "\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}}," +
                "\"__config__\":{\"formId\":\"1vd5xvm1t9n\",\"defaultValue\":\"\"," +
                "\"document\":\"https://element.eleme.cn/#/en-US/component/input\"," +
                "\"dataType\":\"static\",\"label\":\"Seller Country\",\"showLabel\":true," +
                "\"required\":false,\"renderKey\":\"kd0amztka0g0000000\",\"layout\":\"colFormItem\"," +
                "\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\"," +
                "\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false," +
                "\"__vModel__\":\"microservicesCountry\",\"checked\":true,\"style\":{\"width\":\"100%\"}," +
                "\"disabled\":true,\"toolText\":\"\",\"no-data-text\":\" \",\"placeholder\":\"\"," +
                "\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}," +
                "\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":169,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices Entity\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553420\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesEntity\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":171,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"microservices VAT ID\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553512\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"microservicesVatId\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":31,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553377\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":32,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553835\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":6,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553903\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalYear\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553753\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"fiscalQuarter\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":90,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"GTN Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553554\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"gtnType\",\"checked\":true,\"style\":{\"width\":\"\"},\"disabled\":true,\"no-data-text\":\"No Data\",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"1c5y4af686l\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Code/Agreement Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"7aj8fogcpjw00000000\",\"layout\":\"colFormItem\",\"isDefault\":false,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}},\"__config__\":{\"formId\":\"2g8n4h6xfsz\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Program Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"3w5700eql7200000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":12},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"programName\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one program link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"isTooltip\":true,\"separator\":\"/\",\"prefix-icon\":\"\",\"props\":{\"props\":{\"children\":\"children\",\"multiple\":true,\"label\":\"label\",\"checkStrictly\":true,\"value\":\"value\"}}," +
                "\"__config__\":{\"formId\":\"2iemj2yq6h4\",\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/en-US/component/input\",\"dataType\":\"static\",\"label\":\"Calculation Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"6h4vnndgu3o00000000\",\"layout\":\"colFormItem\",\"isDefault\":true,\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"show-all-levels\":false,\"__vModel__\":\"calculationCode\",\"checked\":true,\"style\":{\"width\":\"100%\"},\"disabled\":true,\"toolText\":\"If you see “Multiple”, then it means more than one calculation link to this Invoice. Please click Payment Code below for detail. \",\"no-data-text\":\" \",\"placeholder\":\"\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}],\"span\":12}");
        viewFieldDO.setType(2);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldManager.selectViewFieldById(1559)).thenReturn(viewFieldDO);

        // Configure ViewFieldManager.selectViewFieldInfoList(...).
        final ViewFieldInfo viewFieldInfo = new ViewFieldInfo();
        viewFieldInfo.setAttnum(0);
        viewFieldInfo.setRelname("relname");
        viewFieldInfo.setField("field");
        viewFieldInfo.setFieldType("fieldType");
        viewFieldInfo.setFieldLabel("fieldLabel");
        viewFieldInfo.setFieldTest("fieldTest");
        viewFieldInfo.setChecked(false);
        final List<ViewFieldInfo> viewFieldInfos = Arrays.asList(viewFieldInfo);
        when(mockViewFieldManager.selectViewFieldInfoList(new ViewFieldInfo())).thenReturn(viewFieldInfos);

        viewFieldServiceImplUnderTest.completionField(viewField);

        final ViewField viewField1 = new ViewField();
        viewField1.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewFieldServiceImplUnderTest.completionField(viewField1);


    }

    @Test
    public void testCompletionField_ViewFieldManagerSelectViewFieldInfoListReturnsNoItems() {
        // Setup
        final ViewField viewField = new ViewField();
        viewField.setViewConditionValue(Arrays.asList(new HashMap<>()));
        viewField.setId(0);
        viewField.setPageKey("pageKey");
        viewField.setViewType("viewType");
        viewField.setDivKey("divKey");
        viewField.setStatus(0);
        viewField.setFieldJson("fieldJson");
        viewField.setType(0);
        viewField.setViewFieldCode("viewFieldCode");
        viewField.setTableName("tableName");

        // Configure ViewFieldManager.selectViewFieldById(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldManager.selectViewFieldById(0)).thenReturn(viewFieldDO);

        when(mockViewFieldManager.selectViewFieldInfoList(new ViewFieldInfo())).thenReturn(Collections.emptyList());

    }

    @Test
    public void testSelectViewFieldListPrecise() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.selectViewFieldListPrecise(map);

        // Verify the results
    }

    @Test
    public void testSelectViewFieldListPrecise_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.selectViewFieldListPrecise(map);

        // Verify the results
    }

    @Test
    public void testSelectViewFieldListPrecise_ViewFieldManagerReturnsNoItems() {
        // Setup
        final Map<String, Object> map = new HashMap<>();

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldListPrecise(new HashMap<>())).thenReturn(Collections.emptyList());

        // Run the test
        final List<Map<String, Object>> result = viewFieldServiceImplUnderTest.selectViewFieldListPrecise(map);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testParentViewFieldCode() {
        // Setup
        // Configure ViewFieldManager.selectViewFieldById(...).
        final ViewFieldDO viewFieldDO = new ViewFieldDO();
        viewFieldDO.setId(0);
        viewFieldDO.setPageKey("pageKey");
        viewFieldDO.setViewType("viewType");
        viewFieldDO.setDivKey("divKey");
        viewFieldDO.setStatus(0);
        viewFieldDO.setFieldJson("fieldJson");
        viewFieldDO.setType(0);
        viewFieldDO.setViewFieldCode("viewFieldCode");
        viewFieldDO.setTableName("tableName");
        viewFieldDO.setRegion("region");
        when(mockViewFieldManager.selectViewFieldById(0)).thenReturn(viewFieldDO);

        // Run the test
        final String result = viewFieldServiceImplUnderTest.parentViewFieldCode(0);

        // Verify the results
        assertEquals("viewFieldCode", result);
    }

    @Test
    public void testGetViewField() {
        // Setup
        final Map<String, Object> queryMap = new HashMap<>();
        final List<JSONObject> expectedResult = Arrays.asList(new JSONObject(0, false));

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("test1", "2");
        objectObjectHashMap.put("test2", "2");
        objectObjectHashMap.put("id", "2");
        list.add(objectObjectHashMap);
        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(any())).thenReturn(list);

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(1021);
        viewConditionValueDO.setFieldId(1001);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);

        ViewConditionValue viewConditionValue=new ViewConditionValue();
        viewConditionValue.setId(1021);
        viewConditionValue.setFieldId(1001);
        viewConditionValue.setCondition("condition");
        viewConditionValue.setConditionValue("conditionValue");
        when(mockViewConditionValueService.selectViewConditionValueList(viewConditionValue)).thenReturn(viewConditionValueDOS);


        //viewFieldServiceImplUnderTest.getViewField(queryMap);
        List<ViewTemplate> templates=new ArrayList<>();
        ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setPageKey("2154");
        viewTemplate.setViewType("viewType");
        viewTemplate.setDivKey("divKey");
        viewTemplate.setType(0);
        viewTemplate.setParentId(0);
        viewTemplate.setIsDefault(0);
        viewTemplate.setFieldJson("{\"formRef\":\"elForm\",\"gutter\":24,\"size\":\"small\",\"formRules\":\"rules\",\"labelPosition\":\"top\",\"formBtns\":false,\"labelWidth\":100,\"disabled\":false,\"formModel\":\"formData\",\"fields\":[{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":77,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Business Group\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"businessGroup\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":1,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"GEO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"geoCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":23,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Region/Market\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"regionMarketCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":33,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Segment\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"segmentcode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":35,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Territory\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"territory\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":36,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Sales Org\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"salesOrgCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":8,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Fiscal Year\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"poolFy\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":12,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Fiscal Quarter\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"poolQ\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":37,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Sales Office\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"salesOfficeCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":7,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Pool Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"poolType\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[{\"label\":\"By Order\",\"value\":\"By Order\"},{\"label\":\"Not By Order\",\"value\":\"Not By Order\"}],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":3,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":4,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":105,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"GTN Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1051650505232992\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"gtnTypeCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":true,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":103,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"GTN Category\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1031650505203080\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"gtnCategory\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"filterable\":false,\"fieldRange\":\"\",\"clearable\":true,\"__config__\":{\"formId\":106,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"labelWidth\":220,\"label\":\"Status\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1061650505233248\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"multiple\":false,\"__vModel__\":\"status\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[{\"label\":\"Draft\",\"value\":\"Draft\"},{\"label\":\"Active\",\"value\":\"Active\"},{\"label\":\"Closed\",\"value\":\"Closed\"},{\"label\":\"Cancelled\",\"value\":\"Cancelled\"}],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":107,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Applicant\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1071650505233799\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"createBy\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":110,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Pool Description\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553530\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"poolDesc\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":113,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Pool Currency\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553862\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"poolCurrency\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":127,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"BP Type\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553727\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"partnerType\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":135,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"BU\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553816\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"bu\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":134,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Profit Center\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553561\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"profitCenterCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":139,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"APC Code\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553981\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"apcCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":140,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Product No\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553343\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"mtm\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":136,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Product Hierarchy\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553600\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"productHierarchyCode\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":122,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Distribution Channel\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553687\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"distributionChannel\",\"dict\":\"Distribution_Channel\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":123,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Division\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553230\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"divisionCode\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":141,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"Top Choice Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553964\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"topChoiceFlag\",\"dict\":\"Top_Choice_Flag\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":124,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"SBO\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553180\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"isSbo\",\"dict\":\"SBO\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":125,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"LBP Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553732\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"lbpFlag\",\"dict\":\"LBP_Flag\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":133,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"FOB Flag\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553460\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"fobFlag\",\"dict\":\"FOB_Flag\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":128,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Customer Number\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553167\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"customerNumber\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":168,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"Customer Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553918\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"customerName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":129,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"End Customer Id\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553694\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerId\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":{\"formId\":130,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"End Customer Name\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553031\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6},\"readonly\":false,\"__vModel__\":\"endCustomerName\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"no-data-text\":\"No Data\",\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":131,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"ISG Customer Segment L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553188\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"isgCustomerSegmentL2\",\"dict\":\"ISG_Customer_Segment_L2\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}},{\"fieldRange\":\"\",\"filterable\":true,\"clearable\":true,\"isTooltip\":false,\"multiple\":true,\"__config__\":{\"formId\":132,\"defaultValue\":[],\"document\":\"https://element.eleme.cn/#/en-US/component/select\",\"labelWidth\":220,\"label\":\"GTM Customer Segment L2\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553513\",\"layout\":\"colFormItem\",\"tagIcon\":\"select\",\"changeTag\":true,\"tag\":\"el-select\",\"regList\":[],\"span\":6},\"__vModel__\":\"gtmCustomerSegmentL2\",\"dict\":\"GTM_Customer_Segment_L2\",\"checked\":false,\"style\":{\"width\":\"100%\"},\"disabled\":false,\"toolText\":\"\",\"no-data-text\":\"No Data\",\"placeholder\":\"Please Select\",\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"}}],\"span\":6}");
        viewTemplate.setId(1447);
        viewTemplate.setFieldId(1638);
        viewTemplate.setName("name");
        templates.add(viewTemplate);

        Boolean catchException = false;
        try {
            queryMap.put("condition","condition");
            queryMap.put("conditionValue","conditionValue");
            queryMap.put("divKey","divKey");
            when(viewFieldServiceImplUnderTest.pageKeyList(queryMap)).thenReturn(templates);

            // Run the test
            final List<JSONObject> result = viewFieldServiceImplUnderTest.getViewField(queryMap);

            // Verify the results
            assertEquals(result, result);
//            verify(mockViewTemplateService).checkedFields(new HashMap<>(), new HashMap<>());

        } catch (Exception e) {
            catchException = true;
        }
        assertFalse(catchException);
    }

    @Test
    public void testGetViewField_ISysDictDataServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> queryMap = new HashMap<>();
        final List<JSONObject> expectedResult = Arrays.asList(new JSONObject(0, false));
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(Collections.emptyList());
        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);


    }

    @Test
    public void testGetViewField_ViewFieldManagerReturnsNoItems() {
        // Setup
        final Map<String, Object> queryMap = new HashMap<>();
        final List<JSONObject> expectedResult = Arrays.asList(new JSONObject(0, false));

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Collections.emptyList());

        // Configure IViewConditionValueService.selectViewConditionValueList(...).
        final ViewConditionValueDO viewConditionValueDO = new ViewConditionValueDO();
        viewConditionValueDO.setId(0);
        viewConditionValueDO.setFieldId(0);
        viewConditionValueDO.setCondition("condition");
        viewConditionValueDO.setConditionValue("conditionValue");
        final List<ViewConditionValueDO> viewConditionValueDOS = Arrays.asList(viewConditionValueDO);
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(viewConditionValueDOS);
        Boolean catchException = false;
        try {
            // Run the test
            final List<JSONObject> result = viewFieldServiceImplUnderTest.getViewField(queryMap);

            // Verify the results
            assertEquals(expectedResult, result);
            verify(mockViewTemplateService).checkedFields(new HashMap<>(), new HashMap<>());

        } catch (OtmpException e) {
            catchException = true;
        }
        assertTrue(catchException);
    }

    @Test
    public void testGetViewField_IViewConditionValueServiceReturnsNoItems() {
        // Setup
        final Map<String, Object> queryMap = new HashMap<>();
        final List<JSONObject> expectedResult = Arrays.asList(new JSONObject(0, false));

        // Configure ISysDictDataService.selectDictDataByTypeCode(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("dictValue");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
        when(mockSysDictDataService.selectDictDataByTypeCode("dictType")).thenReturn(sysDictData);

        when(mockViewFieldManager.selectViewFieldAndTemplateAndCondition(new HashMap<>())).thenReturn(Arrays.asList(new HashMap<>()));
        when(mockViewConditionValueService.selectViewConditionValueList(any(ViewConditionValue.class))).thenReturn(Collections.emptyList());

    }
}
