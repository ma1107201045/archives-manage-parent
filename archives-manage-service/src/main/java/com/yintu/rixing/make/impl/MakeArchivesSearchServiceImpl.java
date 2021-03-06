package com.yintu.rixing.make.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataArchivesLibraryFileSearch;
import com.yintu.rixing.data.IDataArchivesLibraryFileSearchService;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.make.IMakeArchivesSearchService;
import com.yintu.rixing.make.MakeArchivesSearchMapper;
import com.yintu.rixing.pojo.MakeArchivesSearchPojo;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;
import com.yintu.rixing.vo.data.DataCommonFieldVo;
import com.yintu.rixing.vo.data.DataCommonVo;
import com.yintu.rixing.vo.make.MakeArchivesSearchVo;
import com.yintu.rixing.warehouse.IWareLibraryTreeService;
import com.yintu.rixing.warehouse.IWareTemplateLibraryFieldService;
import com.yintu.rixing.warehouse.WareTemplateLibraryField;
import com.yintu.rixing.warehouse.WareTemplateLibraryFiledMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:59:59
 * @Version: 1.0
 */
@Service
public class MakeArchivesSearchServiceImpl implements IMakeArchivesSearchService {
    @Autowired
    private MakeArchivesSearchMapper makeArchivesSearchMapper;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private IDataFormalLibraryService iDataFormalLibraryService;
    @Autowired
    private IWareLibraryTreeService iWareLibraryTreeService;
    @Autowired
    private WareTemplateLibraryFiledMapper wareTemplateLibraryFiledMapper;
    @Autowired
    private ISysDataTypeService iSysTemplateLibraryFieldTypeService;
    @Autowired
    private IWareTemplateLibraryFieldService iWareTemplateLibraryFieldService;
    @Autowired
    private ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;
    @Autowired
    protected ISysDepartmentService iSysDepartmentService;
    @Autowired
    private SysArchivesLibraryMapper sysArchivesLibraryMapper;

    @Override
    public DataCommonVo findElectronicsDatasBySomethings(Integer num, Integer size, Integer archiveId, String searchThings) {
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archiveId);
        String dataKey1 = sysArchivesLibrary.getDataKey();
        String fullTableName = TableNameUtil.getFullTableName(dataKey1);
        if (wareTemplateLibraryFiledMapper.findTable(fullTableName) == 0) {
            throw new BaseRuntimeException("请先创建实相关表");
        } else {
            DataCommonVo dataCommonVo = new DataCommonVo();
            List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();

            List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryId(archiveId);
            for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {
                String dataKey = sysArchivesLibraryField.getDataKey();
                String name = sysArchivesLibraryField.getName();
                SysDataType sysTemplateLibraryFieldType = sysArchivesLibraryField.getSysDataType();
                Integer fieldTypeId = sysTemplateLibraryFieldType.getId();
                String fieldTypeDataKey = sysTemplateLibraryFieldType.getDataKey();
                String fieldTypeName = sysTemplateLibraryFieldType.getName();

                Short query = sysArchivesLibraryField.getQuery();
                Short title = sysArchivesLibraryField.getTitle();
                Short form = sysArchivesLibraryField.getForm();

                DataCommonFieldVo dataCommonTitleVo = new DataCommonFieldVo();
                dataCommonTitleVo.setProp(dataKey);
                dataCommonTitleVo.setLabel(name);

                dataCommonTitleVo.setTypeId(fieldTypeId);
                dataCommonTitleVo.setTypeProp(fieldTypeDataKey);
                dataCommonTitleVo.setTypeLabel(fieldTypeName);
                dataCommonTitleVo.setNotNull(sysArchivesLibraryField.getRequired().equals(EnumFlag.True.getValue()));

                dataCommonTitleVo.setQuery(query.equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setTitle(title.equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setForm(form.equals(EnumFlag.True.getValue()));
                dataCommonFieldVos.add(dataCommonTitleVo);
            }

            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            Page<Map<String, Object>> entityArchivesPages = makeArchivesSearchMapper.findElectronicsDatasBySomethings(page, searchThings, fullTableName);
            //特殊字段需要处理
            entityArchivesPages.getRecords().forEach(map -> {
                String dataKey = EnumArchivesLibraryDefaultField.DEPARTMENT_ID.getDataKey();
                if (map.containsKey(dataKey)) {
                    Integer departmentId = (Integer) map.get(dataKey);
                    SysDepartment sysDepartment = iSysDepartmentService.getById(departmentId);
                    map.put(dataKey, sysDepartment.getName());
                }
            });

            dataCommonVo.setFields(dataCommonFieldVos);
            dataCommonVo.setPage(entityArchivesPages);
            return dataCommonVo;
        }
    }

    @Autowired
    private IDataArchivesLibraryFileSearchService iDataArchivesLibraryFileSearchService;

    @Override
    public DataCommonVo searchEntityArchives(Integer num, Integer size, String searchThings, Short searchType, Short userType, Integer userId) {
        String tableName = "ware_physical_warehouse";
        if (wareTemplateLibraryFiledMapper.findTable(tableName) == 0) {
            throw new BaseRuntimeException("请先创建实体库表");
        } else {
            List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();
            List<WareTemplateLibraryField> wareTemplateLibraryFieldList = iWareTemplateLibraryFieldService.list();
            for (WareTemplateLibraryField wareTemplateLibraryField : wareTemplateLibraryFieldList) {
                DataCommonFieldVo dataCommonTitleVo = new DataCommonFieldVo();
                Integer templateLibraryFieldTypeId = wareTemplateLibraryField.getTemplateLibraryFieldTypeId();
                SysDataType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
                Integer fieldTypeId = sysTemplateLibraryFieldType.getId();
                String fieldTypeDataKey = sysTemplateLibraryFieldType.getDataKey();
                String fieldTypeName = sysTemplateLibraryFieldType.getName();
                dataCommonTitleVo.setTypeId(fieldTypeId);
                dataCommonTitleVo.setTypeProp(fieldTypeDataKey);
                dataCommonTitleVo.setTypeLabel(fieldTypeName);
                dataCommonTitleVo.setNotNull(wareTemplateLibraryField.getRequired() == 1);
                dataCommonTitleVo.setProp(wareTemplateLibraryField.getDataKey());
                dataCommonTitleVo.setLabel(wareTemplateLibraryField.getName());
                dataCommonTitleVo.setQuery(wareTemplateLibraryField.getQuery().equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setTitle(wareTemplateLibraryField.getTitle().equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setForm(wareTemplateLibraryField.getForm().equals(EnumFlag.True.getValue()));
                dataCommonFieldVos.add(dataCommonTitleVo);
            }
            DataCommonFieldVo dataCommonTitleVo1 = new DataCommonFieldVo();
            dataCommonTitleVo1.setTypeProp("datetime");
            dataCommonTitleVo1.setTypeLabel("日期时间框");
            dataCommonTitleVo1.setNotNull(true);
            dataCommonTitleVo1.setProp("inWarehouseTime");
            dataCommonTitleVo1.setLabel("入库时间");
            dataCommonTitleVo1.setQuery(false);
            dataCommonTitleVo1.setTitle(true);
            dataCommonTitleVo1.setForm(false);
            dataCommonTitleVo1.setTypeId(6);
            DataCommonFieldVo dataCommonTitleVo2 = new DataCommonFieldVo();
            dataCommonTitleVo2.setTypeProp("datetime");
            dataCommonTitleVo2.setTypeLabel("日期时间框");
            dataCommonTitleVo2.setNotNull(true);
            dataCommonTitleVo2.setProp("outWarehouseTime");
            dataCommonTitleVo2.setLabel("出库时间");
            dataCommonTitleVo2.setQuery(false);
            dataCommonTitleVo2.setTitle(true);
            dataCommonTitleVo2.setForm(false);
            dataCommonTitleVo2.setTypeId(6);
            DataCommonFieldVo dataCommonTitleVo3 = new DataCommonFieldVo();
            dataCommonTitleVo3.setTypeProp("varchar");
            dataCommonTitleVo3.setTypeLabel("文本框(文本)");
            dataCommonTitleVo3.setNotNull(true);
            dataCommonTitleVo3.setProp("archivesNum");
            dataCommonTitleVo3.setLabel("档案号");
            dataCommonTitleVo3.setQuery(true);
            dataCommonTitleVo3.setTitle(true);
            dataCommonTitleVo3.setForm(false);
            dataCommonTitleVo3.setTypeId(1);
            dataCommonFieldVos.add(0, dataCommonTitleVo3);
            dataCommonFieldVos.add(dataCommonTitleVo1);
            dataCommonFieldVos.add(dataCommonTitleVo2);
            DataCommonFieldVo dataCommonTitleVo4 = new DataCommonFieldVo();
            dataCommonTitleVo4.setTypeProp("int");
            dataCommonTitleVo4.setTypeLabel("文本框(数值)");
            dataCommonTitleVo4.setNotNull(true);
            dataCommonTitleVo4.setProp("ware_library_tree_id");
            dataCommonTitleVo4.setLabel("库房");
            dataCommonTitleVo4.setQuery(false);
            dataCommonTitleVo4.setTitle(false);
            dataCommonTitleVo4.setForm(true);
            dataCommonTitleVo4.setTypeId(5);
            dataCommonFieldVos.add(dataCommonTitleVo4);
            DataCommonFieldVo dataCommonTitleVo5 = new DataCommonFieldVo();
            dataCommonTitleVo5.setTypeProp("varchar");
            dataCommonTitleVo5.setTypeLabel("文本框(文本)");
            dataCommonTitleVo5.setNotNull(true);
            dataCommonTitleVo5.setProp("archivesName");
            dataCommonTitleVo5.setLabel("档案名称");
            dataCommonTitleVo5.setQuery(true);
            dataCommonTitleVo5.setTitle(true);
            dataCommonTitleVo5.setForm(true);
            dataCommonTitleVo5.setTypeId(1);
            dataCommonFieldVos.add(1, dataCommonTitleVo5);

            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            Page<Map<String, Object>> entityArchivesPages = wareTemplateLibraryFiledMapper.searchEntityArchives(page, searchThings);
            List<DataArchivesLibraryFileSearch> dataArchivesLibraryFileSearches = new ArrayList<>();
            for (Map<String, Object> record : entityArchivesPages.getRecords()) {
                List<Integer> integerList = new ArrayList<>();
                Integer ware_library_tree_id = (Integer) record.get("ware_library_tree_id");
                integerList.add(ware_library_tree_id);
                Integer parentId = iWareLibraryTreeService.findParentId(ware_library_tree_id);
                if (parentId != -1)
                    integerList.add(parentId);
                for (int i = 0; i < 20; i++) {
                    if (parentId != -1) {
                        Integer parentId1 = iWareLibraryTreeService.findParentId(parentId);
                        if (parentId1 != -1)
                            integerList.add(parentId1);
                        parentId = parentId1;
                    }
                }
                Arrays.sort(integerList.toArray(), Collections.reverseOrder());
                record.put("warelibrarytreeid", integerList);
                //用户添加查询记录
                DataArchivesLibraryFileSearch dataArchivesLibraryFileSearch = new DataArchivesLibraryFileSearch();
                dataArchivesLibraryFileSearch.setSearchType(searchType);
                dataArchivesLibraryFileSearch.setArchivesLibraryFileId((Integer) record.get("id"));
                dataArchivesLibraryFileSearch.setUserType(userType);
                dataArchivesLibraryFileSearch.setUserId(userId);
                dataArchivesLibraryFileSearches.add(dataArchivesLibraryFileSearch);
            }
            dataCommonVo.setPage(entityArchivesPages);
            //查询出来的文件集合用于保存查询记录
            iDataArchivesLibraryFileSearchService.saveBatch(dataArchivesLibraryFileSearches);
            return dataCommonVo;
        }
    }

    @Override
    public Page<MakeArchivesSearchPojo> searchArchivesFileByIds(Integer num, Integer size, Integer archivesDirectoryId, Integer archivesLibId) {
        Page<MakeArchivesSearchPojo> makeArchivesSearchPojoPage = makeArchivesSearchMapper.selectFileByIds(new Page<>(num, size),archivesDirectoryId,archivesLibId);
        return makeArchivesSearchPojoPage;
    }

    @Override
    public Page<MakeArchivesSearchPojo> page(Integer num,  Integer size, String searchThings, Integer archivesDirectoryId, Integer archivesLibId) {
        Page<MakeArchivesSearchPojo> makeArchivesSearchPojoPage = makeArchivesSearchMapper.selectFileByWords(new Page<>(num, size), searchThings,archivesDirectoryId,archivesLibId);
        return makeArchivesSearchPojoPage;
    }

    @Override
    public Page<MakeArchivesSearchVo> findDatasBySomethings(Integer num, Integer size, Integer archiveId, String searchThings) {
        Page<MakeArchivesSearchVo> page1 = new Page<>();
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archiveId);
        String dataKey1 = sysArchivesLibrary.getDataKey();
        String fullTableName = TableNameUtil.getFullTableName(dataKey1);
        if (wareTemplateLibraryFiledMapper.findTable(fullTableName) == 0) {
            throw new BaseRuntimeException("请先创建实相关表");
        } else {
            List<MakeArchivesSearchVo> list=new ArrayList<>();
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            Page<Map<Object, String>> entityArchivesPages = makeArchivesSearchMapper.findDatasBySomethings(page, fullTableName,searchThings);
            List<Map<Object, String>> archivesPagesRecords = entityArchivesPages.getRecords();
            BeanUtil.copyProperties(entityArchivesPages, page1, "records");
            for (Map<Object, String> archivesPagesRecord : archivesPagesRecords) {
                String archives_code = archivesPagesRecord.get("archives_code");//档号
                String folder_code = archivesPagesRecord.get("folder_code");//案卷号
                String file_code = archivesPagesRecord.get("file_code");//件号
                String title = archivesPagesRecord.get("title");//提名
                String year =String.valueOf(archivesPagesRecord.get("year"));//年度
                String responsible_person = archivesPagesRecord.get("responsible_person");//责任者
                String id =String.valueOf(archivesPagesRecord.get("id")) ;//文档id

                MakeArchivesSearchVo makeArchivesSearchVo=new MakeArchivesSearchVo();
                makeArchivesSearchVo.setArchivesDirectoryId(Integer.parseInt(id));
                makeArchivesSearchVo.setArchivesLibId(archiveId);
                makeArchivesSearchVo.setArchivesCode(archives_code);
                makeArchivesSearchVo.setFolderCode(folder_code);
                makeArchivesSearchVo.setFileCode(file_code);
                makeArchivesSearchVo.setTitle(title);
                makeArchivesSearchVo.setYear(year);
                makeArchivesSearchVo.setResponsiblePerson(responsible_person);
                makeArchivesSearchVo.setArchivesFile(sysArchivesLibrary.getName());
                list.add(makeArchivesSearchVo);
            }
            page1.setRecords(list);
            return page1;
        }
    }

    @Override
    public Page<MakeArchivesSearchVo> listArchivesByKeyWord(MakeArchivesSearchDto makeArchivesSearchDto) {
        Page<MakeArchivesSearchVo> page1 = new Page<>();
        Integer num = makeArchivesSearchDto.getNum();
        Integer size = makeArchivesSearchDto.getSize();
        String keyWord = makeArchivesSearchDto.getKeyWord();
        Short searchType = makeArchivesSearchDto.getSearchType();
        Short userType = makeArchivesSearchDto.getUserType();
        Integer userId = makeArchivesSearchDto.getUserId();
        Page<MakeArchivesSearchPojo> page2 = makeArchivesSearchMapper.selectFileByKeyWord(new Page<>(num, size), keyWord);
        BeanUtil.copyProperties(page2, page1, "records");
        List<MakeArchivesSearchPojo> makeArchivesSearchPojos = page2.getRecords();
        List<MakeArchivesSearchVo> list=new ArrayList<>();
        List<DataArchivesLibraryFileSearch> dataArchivesLibraryFileSearches = new ArrayList<>();
        for (MakeArchivesSearchPojo makeArchivesSearchPojo : makeArchivesSearchPojos) {
            Integer archivesLibId = makeArchivesSearchPojo.getArchivesLibId();
            Integer archivesDirectoryId = makeArchivesSearchPojo.getArchivesDirectoryId();
            SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibId);
            String dataKey = sysArchivesLibrary.getDataKey();
            String fullTableName = TableNameUtil.getFullTableName(dataKey);
            Map<Object,String> map=sysArchivesLibraryMapper.findHourseData(fullTableName,archivesDirectoryId);
            System.out.println("mapppppp="+map);
            String archives_code = map.get("archives_code");//档号
            String folder_code = map.get("folder_code");//案卷号
            String file_code = map.get("file_code");//件号
            String title = map.get("title");//提名
            String year =String.valueOf(map.get("year"));//年度
            String responsible_person = map.get("responsible_person");//责任者

            MakeArchivesSearchVo makeArchivesSearchVo=new MakeArchivesSearchVo();
            makeArchivesSearchVo.setArchivesDirectoryId(archivesDirectoryId);
            makeArchivesSearchVo.setArchivesLibId(archivesLibId);
            makeArchivesSearchVo.setArchivesCode(archives_code);
            makeArchivesSearchVo.setFolderCode(folder_code);
            makeArchivesSearchVo.setFileCode(file_code);
            makeArchivesSearchVo.setTitle(title);
            makeArchivesSearchVo.setYear(year);
            makeArchivesSearchVo.setResponsiblePerson(responsible_person);
            makeArchivesSearchVo.setArchivesFile(sysArchivesLibrary.getName());
            list.add(makeArchivesSearchVo);

            //用户添加查询记录
            DataArchivesLibraryFileSearch dataArchivesLibraryFileSearch = new DataArchivesLibraryFileSearch();
            dataArchivesLibraryFileSearch.setSearchType(searchType);
            dataArchivesLibraryFileSearch.setArchivesLibraryFileId(archivesDirectoryId);
            dataArchivesLibraryFileSearch.setUserType(userType);
            dataArchivesLibraryFileSearch.setUserId(userId);
            dataArchivesLibraryFileSearches.add(dataArchivesLibraryFileSearch);
        }
        //查询出来的文件集合用于保存查询记录
        iDataArchivesLibraryFileSearchService.saveBatch(dataArchivesLibraryFileSearches);
        page1.setRecords(list);
        return page1;
    }

    @Override
    public Page<MakeArchivesSearchElectronicVo> listElectronicByKeyWord(MakeArchivesSearchDto makeArchivesSearchDto) {
        Page<MakeArchivesSearchElectronicVo> page1 = new Page<>();
        Integer num = makeArchivesSearchDto.getNum();
        Integer size = makeArchivesSearchDto.getSize();
        String keyWord = makeArchivesSearchDto.getKeyWord();
        Short searchType = makeArchivesSearchDto.getSearchType();
        Short userType = makeArchivesSearchDto.getUserType();
        Integer userId = makeArchivesSearchDto.getUserId();
        Page<MakeArchivesSearchPojo> page2 = makeArchivesSearchMapper.selectByKeyWord(new Page<>(num, size), keyWord);
        BeanUtil.copyProperties(page2, page1, "records");
        List<MakeArchivesSearchPojo> makeArchivesSearchPojos = page2.getRecords();
        List<MakeArchivesSearchElectronicVo> makeArchivesSearchVos = new ArrayList<>();
        List<DataArchivesLibraryFileSearch> dataArchivesLibraryFileSearches = new ArrayList<>();
        for (MakeArchivesSearchPojo makeArchivesSearchPojo : makeArchivesSearchPojos) {
            Integer archivesLibId = makeArchivesSearchPojo.getArchivesLibId();
            Integer archivesDirectoryId = makeArchivesSearchPojo.getArchivesDirectoryId();
            MakeArchivesSearchElectronicVo makeArchivesSearchVo = new MakeArchivesSearchElectronicVo();
            BeanUtil.copyProperties(makeArchivesSearchPojo, makeArchivesSearchVo);
            SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibId);
            makeArchivesSearchVo.setArchivesLibName(sysArchivesLibrary.getName());
            Map<String, Object> map = iDataFormalLibraryService.getById(archivesDirectoryId, archivesLibId);
            if (map != null) {
                makeArchivesSearchVo.setArchivesDirectoryNum((String) map.get(EnumArchivesLibraryDefaultField.ARCHIVES_CODE.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectoryTopicName((String) map.get(EnumArchivesLibraryDefaultField.TOPIC_NAME.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectoryRetentionPeriod(map.get(EnumArchivesLibraryDefaultField.RETENTION_PERIOD.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectoryValidPeriod(map.get(EnumArchivesLibraryDefaultField.VALID_PERIOD.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectorySecurityLevel(map.get(EnumArchivesLibraryDefaultField.SECURITY_LEVEL.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectoryFilingAnnual((String) map.get(EnumArchivesLibraryDefaultField.FILING_ANNUAL.getDataKey()));
            }
            makeArchivesSearchVos.add(makeArchivesSearchVo);
            //用户添加查询记录
            DataArchivesLibraryFileSearch dataArchivesLibraryFileSearch = new DataArchivesLibraryFileSearch();
            dataArchivesLibraryFileSearch.setSearchType((short)1);
            dataArchivesLibraryFileSearch.setArchivesLibraryFileId(makeArchivesSearchPojo.getArchivesFileId());
            dataArchivesLibraryFileSearch.setUserType(userType);
            dataArchivesLibraryFileSearch.setUserId(userId);
            dataArchivesLibraryFileSearches.add(dataArchivesLibraryFileSearch);
        }
        page1.setRecords(makeArchivesSearchVos);
        //查询出来的文件集合用于保存查询记录
        iDataArchivesLibraryFileSearchService.saveBatch(dataArchivesLibraryFileSearches);
        return page1;
    }

}
