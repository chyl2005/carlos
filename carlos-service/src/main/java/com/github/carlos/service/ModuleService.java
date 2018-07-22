package com.github.carlos.service;

import com.github.carlos.common.constant.DeleteStatusEnum;
import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.QueryMenuParam;
import com.github.carlos.dal.bean.MenuDO;
import com.github.carlos.dal.mapper.MenuMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenyanlong
 * @ClassName: ModuleService
 * @Description: 系统模块业务处理
 * @date 2015年11月23日 下午2:30:03
 */
@Service
public class ModuleService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * @return List<ModuleEntity> 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月23日 下午2:33:08
     */
    public List<MenuDO> getAllModules() {
        return this.menuMapper.selectAll();
    }


    /**
     * @param parentId
     * @return ModuleEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月25日 下午5:45:12
     */
    public List<MenuDO> getModules(Integer parentId) {

        QueryMenuParam menuParam = new QueryMenuParam();
        menuParam.setParentId(parentId);
        List<MenuDO> menuDOS = menuMapper.selectByParam(menuParam);
        return menuDOS;
    }

    /**
     * @param parentId
     * @return AoData 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月25日 下午5:45:18
     */
    public AoData getPageModules(Integer parentId) {
        QueryMenuParam menuParam = new QueryMenuParam();
        menuParam.setParentId(parentId);
        List<MenuDO> menuDOS = menuMapper.selectByParam(menuParam);
        Integer count = menuMapper.selectByCount(menuParam);
        AoData aoData = new AoData(count, menuDOS);
        return aoData;
    }


    /**
     * @param menu
     * @return ModuleEntity 返回类型
     * @Description: 保存
     * @author chenyanlong
     * @date 2015年11月27日 下午3:00:40
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrUpdate(MenuDO menu) {

        this.menuMapper.insertOrUpdate(menu);

    }

    /**
     * @param moduleId
     * @return Boolean 返回类型
     * @Description: 逻辑删除
     * @author chenyanlong
     * @date 2015年11月27日 下午3:00:58
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void del(Integer moduleId) {
        this.menuMapper.updateStatus(moduleId, DeleteStatusEnum.DEL.getCode());
    }

    /**
     * @param level
     * @return List<ModuleEntity> 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月25日 下午5:45:18
     */

    public List<MenuDO> getModulesByLevel(Integer level) {

        QueryMenuParam menuParam = new QueryMenuParam();
        menuParam.setLevel(level);
        return this.menuMapper.selectByParam(menuParam);


    }

    /**
     * @param id
     * @return ModuleEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月27日 下午4:55:55
     */
    public MenuDO findById(Integer id) {
        return this.menuMapper.findById(id);
    }

    /**
     * @param url
     * @return ModuleEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年12月10日 下午4:26:52
     */
    public MenuDO getModuleByPath(String url) {
        QueryMenuParam menuParam = new QueryMenuParam();
        menuParam.setPath(url);
        List<MenuDO> menuDOS = menuMapper.selectByParam(menuParam);
        if (CollectionUtils.isNotEmpty(menuDOS)) {
            return menuDOS.get(0);
        }
        return null;

    }

}
