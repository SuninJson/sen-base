# Sen Base


<a name="overview"></a>
## 概览
接口描述


### 版本信息
*版本* : 1.0


### 许可信息
*许可证* : http://springfox.github.io/springfox/docs/current/  
*许可网址* : http://springfox.github.io/springfox/docs/current/  
*服务条款* : termsOfServiceUrl


### URI scheme
*域名* : localhost:8080  
*基础路径* : /


### 标签

* sys-role-controller : Sys Role Controller
* view-user-controller : View User Controller




<a name="paths"></a>
## 资源

<a name="sys-role-controller_resource"></a>
### Sys-role-controller
Sys Role Controller


<a name="listusingpost"></a>
#### 通用列表查询接口
```
POST /test/sys-role/list
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**multiQueryCondition**  <br>*可选*|多个查询条件（可为NULL）|[MultiQueryCondition](#multiquerycondition)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< [SysRole](#sysrole) > array|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/test/sys-role/list
```


###### 请求 body
```json
{
  "queryConditionList" : [ {
    "column" : "string",
    "operation" : "string",
    "valueList" : [ "object" ]
  } ]
}
```


##### HTTP响应示例

###### 响应 200
```json
[ {
  "operateTime" : "string",
  "operator" : "string",
  "remark" : "string",
  "roleCode" : "string",
  "roleName" : "string",
  "roleType" : 0,
  "status" : 0
} ]
```


<a name="pagingqueryusingpost"></a>
#### 通用分页查询接口
```
POST /test/sys-role/paging-query
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**currentPage**  <br>*必填*|当前页码|integer (int64)|
|**Query**|**size**  <br>*必填*|页面条目数|integer (int64)|
|**Body**|**multiQueryCondition**  <br>*可选*|多个查询条件（可为NULL）|[MultiQueryCondition](#multiquerycondition)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[IPage«SysRole»](#62c24e636a42f186256d4cb40d24f8fd)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/test/sys-role/paging-query?currentPage=0&size=0
```


###### 请求 body
```json
{
  "queryConditionList" : [ {
    "column" : "string",
    "operation" : "string",
    "valueList" : [ "object" ]
  } ]
}
```


##### HTTP响应示例

###### 响应 200
```json
{
  "current" : 0,
  "pages" : 0,
  "records" : [ {
    "operateTime" : "string",
    "operator" : "string",
    "remark" : "string",
    "roleCode" : "string",
    "roleName" : "string",
    "roleType" : 0,
    "status" : 0
  } ],
  "searchCount" : true,
  "size" : 0,
  "total" : 0
}
```


<a name="view-user-controller_resource"></a>
### View-user-controller
View User Controller


<a name="listusingpost_1"></a>
#### 通用列表查询接口
```
POST /test/view-user/list
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**multiQueryCondition**  <br>*可选*|多个查询条件（可为NULL）|[MultiQueryCondition](#multiquerycondition)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< [ViewUser](#viewuser) > array|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/test/view-user/list
```


###### 请求 body
```json
{
  "queryConditionList" : [ {
    "column" : "string",
    "operation" : "string",
    "valueList" : [ "object" ]
  } ]
}
```


##### HTTP响应示例

###### 响应 200
```json
[ {
  "operateTime" : "string",
  "operationCertificateUrl" : "string",
  "operator" : "string",
  "roleCode" : "string",
  "roleName" : "string",
  "roleType" : 0,
  "staffCardUrl" : "string",
  "status" : 0,
  "userCode" : "string",
  "userName" : "string",
  "userPassword" : "string",
  "userPhone" : "string",
  "userPictureUrl" : "string"
} ]
```


<a name="pagingqueryusingpost_1"></a>
#### 通用分页查询接口
```
POST /test/view-user/paging-query
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**currentPage**  <br>*必填*|当前页码|integer (int64)|
|**Query**|**size**  <br>*必填*|页面条目数|integer (int64)|
|**Body**|**multiQueryCondition**  <br>*可选*|多个查询条件（可为NULL）|[MultiQueryCondition](#multiquerycondition)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[IPage«ViewUser»](#36d43165c016f27f4aecdb3f69761da8)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/test/view-user/paging-query?currentPage=0&size=0
```


###### 请求 body
```json
{
  "queryConditionList" : [ {
    "column" : "string",
    "operation" : "string",
    "valueList" : [ "object" ]
  } ]
}
```


##### HTTP响应示例

###### 响应 200
```json
{
  "current" : 0,
  "pages" : 0,
  "records" : [ {
    "operateTime" : "string",
    "operationCertificateUrl" : "string",
    "operator" : "string",
    "roleCode" : "string",
    "roleName" : "string",
    "roleType" : 0,
    "staffCardUrl" : "string",
    "status" : 0,
    "userCode" : "string",
    "userName" : "string",
    "userPassword" : "string",
    "userPhone" : "string",
    "userPictureUrl" : "string"
  } ],
  "searchCount" : true,
  "size" : 0,
  "total" : 0
}
```




<a name="definitions"></a>
## 定义

<a name="62c24e636a42f186256d4cb40d24f8fd"></a>
### IPage«SysRole»

|名称|说明|类型|
|---|---|---|
|**current**  <br>*可选*|**样例** : `0`|integer (int64)|
|**pages**  <br>*可选*|**样例** : `0`|integer (int64)|
|**records**  <br>*可选*|**样例** : `[ "[sysrole](#sysrole)" ]`|< [SysRole](#sysrole) > array|
|**searchCount**  <br>*可选*|**样例** : `true`|boolean|
|**size**  <br>*可选*|**样例** : `0`|integer (int64)|
|**total**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="36d43165c016f27f4aecdb3f69761da8"></a>
### IPage«ViewUser»

|名称|说明|类型|
|---|---|---|
|**current**  <br>*可选*|**样例** : `0`|integer (int64)|
|**pages**  <br>*可选*|**样例** : `0`|integer (int64)|
|**records**  <br>*可选*|**样例** : `[ "[viewuser](#viewuser)" ]`|< [ViewUser](#viewuser) > array|
|**searchCount**  <br>*可选*|**样例** : `true`|boolean|
|**size**  <br>*可选*|**样例** : `0`|integer (int64)|
|**total**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="multiquerycondition"></a>
### MultiQueryCondition

|名称|说明|类型|
|---|---|---|
|**queryConditionList**  <br>*可选*|**样例** : `[ "[singlequerycondition](#singlequerycondition)" ]`|< [SingleQueryCondition](#singlequerycondition) > array|


<a name="singlequerycondition"></a>
### SingleQueryCondition

|名称|说明|类型|
|---|---|---|
|**column**  <br>*可选*|**样例** : `"string"`|string|
|**operation**  <br>*可选*|**样例** : `"string"`|string|
|**valueList**  <br>*可选*|**样例** : `[ "object" ]`|< object > array|


<a name="sysrole"></a>
### SysRole

|名称|说明|类型|
|---|---|---|
|**operateTime**  <br>*可选*|**样例** : `"string"`|string (date-time)|
|**operator**  <br>*可选*|**样例** : `"string"`|string|
|**remark**  <br>*可选*|**样例** : `"string"`|string|
|**roleCode**  <br>*可选*|**样例** : `"string"`|string|
|**roleName**  <br>*可选*|**样例** : `"string"`|string|
|**roleType**  <br>*可选*|**样例** : `0`|integer (int32)|
|**status**  <br>*可选*|**样例** : `0`|integer (int32)|


<a name="viewuser"></a>
### ViewUser

|名称|说明|类型|
|---|---|---|
|**operateTime**  <br>*可选*|**样例** : `"string"`|string (date-time)|
|**operationCertificateUrl**  <br>*可选*|**样例** : `"string"`|string|
|**operator**  <br>*可选*|**样例** : `"string"`|string|
|**roleCode**  <br>*可选*|**样例** : `"string"`|string|
|**roleName**  <br>*可选*|**样例** : `"string"`|string|
|**roleType**  <br>*可选*|**样例** : `0`|integer (int32)|
|**staffCardUrl**  <br>*可选*|**样例** : `"string"`|string|
|**status**  <br>*可选*|**样例** : `0`|integer (int32)|
|**userCode**  <br>*可选*|**样例** : `"string"`|string|
|**userName**  <br>*可选*|**样例** : `"string"`|string|
|**userPassword**  <br>*可选*|**样例** : `"string"`|string|
|**userPhone**  <br>*可选*|**样例** : `"string"`|string|
|**userPictureUrl**  <br>*可选*|**样例** : `"string"`|string|





