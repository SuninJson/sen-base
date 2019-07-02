package ${package.Controller};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import com.baomidou.mybatisplus.extension.service.IService;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.sen.framework.common.controller.BaseControllerQueryable;


/**
* <p>
 * ${table.comment!} 前端控制器
 * </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
 @RestController
<#else>
 @Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
 class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
 <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}>  implements BaseControllerQueryable<${entity}>{
 <#else>
public class ${table.controllerName} implements BaseControllerQueryable<${entity}> {
 </#if>

  private final ${table.serviceName} service;

  @Autowired
  public ${table.controllerName}(${table.serviceName} service) {
    this.service = service;
  }

  @Override
  public IService<${entity}> service() {
    return service;
  }

 }
</#if>
