package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-12-18
 */
public interface ISysLibraryService extends IService<SysLibrary> {
    List<Map<String, Object>> findById(Integer id);
    List<SysArchives> findByIdScrchives(Integer id);

    List<SysLibrary> findBylnameAndlclasses(String libraryname, String libraryclasses);


    Integer editfield(Integer id, String laofield, String newfield, String type, String fieldlength);

    Map<String, Object> innertFleId(Integer id, SysTableMessge sysTableMessge);

    Map<String, Object> delfield(Integer id, String field);

    Map<String, Object> xsfield(Integer id, String sfieldName, String mfieldName, String valuetype, String fieldlength);

    Map<String, Object> xxfield(Integer id, String xfieldName, String mfieldName, String valuetype, String fieldlength);

    Map<String, Object> findBytepname(String tepname, Integer id,String librname);

}
