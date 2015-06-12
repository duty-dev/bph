<%@ page language="java" pageEncoding="UTF-8"%>
<div id="navigationLeft">
        <div class="line1-mid box">
          <div class="t1-start"></div>
          <div class="end"></div>
        </div>
        <div class="pull-left box">
          <div>
            <div class="title box"></div>
            <div class="clear">
            </div>
          </div>
        </div>
        <div class="pull-right box">
          <div class="line7"></div>
        </div>
        <div class="tree-box clear"><!----内容---->
        <div>
        <table><c:forEach var="item" items="${iconTypeList}">
            <tr>
              <td>
              <td><a href = "#" onclick="reload(${item.id},'${item.name}')">${item.name}</a></td>
            </tr>
            </c:forEach></table>
		</div>
        </div><!----机构树结束---->
        <div class="line8 box"></div>
        <div class="line2"></div>
</div>        