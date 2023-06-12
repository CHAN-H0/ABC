<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<style>
    body {
        margin: 2px;
    }
    .main-container {
        max-width: calc(100% - 4px);
        margin: 0 auto;
        padding: 2px;
    }
    .custom-table {
        margin-bottom: 20px;
    }

    .custom-table {
        border-collapse: collapse;
    }

    .custom-table th,
    .custom-table td {
        border: 1px solid #d3d3d3;
    }

    .custom-table tr.second-row th {
        height: 80px;
        vertical-align: middle;
    }


</style>


<div class="main-container">
    <h1 class="display-5 font-weight-bold">와이파이 정보 구하기</h1>

<div class="menu-area" style="display: flex; gap: 10px; margin-top: 20px; margin-bottom: 20px;">
    <div class="home"><a href="home-servlet">홈</a></div>
    <div>|</div>
    <div class="history"><a href="view-history-servlet">위치 히스토리 목록</a></div>
    <div>|</div>
    <div class="getApiInfos"><a href="api-get-servlet">Open API 와이파이 정보 가져오기</a></div>
</div>
<form action="filter-servlet" method="get" onsubmit="return validateForm()">
    <div class="input-area" style="display: flex; gap: 20px; margin-bottom: 20px;">
        <div>
            <b>LAT: </b><input type="number" step="any" id="LAT" name="latitude"/>
        </div>
        <div>
            <b>LNT: </b><input type="number" step="any" id="LNT" name="longitude"/>
        </div>
        <button type="button" id="getMyLocation">내 위치 가져오기</button>
        <button type="submit">근처 WIFI 정보 보기</button>
    </div>
</form>
<c:if test="${not empty includedPage}">

        <table class="table custom-table">
            <thead class="thead-green">
            <tr style="background-color: #3CB371; color: white;">
                <th scope="col">ID</th>
                <th scope="col">X좌표</th>
                <th scope="col">Y좌표</th>
                <th scope="col">조회일자</th>
                <th scope="col">비고</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty result}">
                <c:forEach var="item" items="${result}">
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.latitude}</td>
                        <td>${item.longitude}</td>
                        <td>${item.dateTimeString}</td>
                        <td>
                            <button type="button" id="historyDelete" onclick="historyDelete(${item.id})">삭제</button>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty result}">
                <tr>
                    <td colspan="5" style="text-align: center;">히스토리 내역이 없습니다.</td>
                </tr>
            </c:if>
            </tbody>
        </table>

</c:if>
<c:if test="${empty includedPage}">

        <table class="table custom-table">
            <thead class="thead-green">
            <tr style="background-color: #3CB371; color: white;">
                <th scope="col">거리(Km)</th>
                <th scope="col">관리번호</th>
                <th scope="col">자치구</th>
                <th scope="col">와이파이명</th>
                <th scope="col">도로명 주소</th>
                <th scope="col">상세주소</th>
                <th scope="col">설치위치(층)</th>
                <th scope="col">설치유형</th>
                <th scope="col">설치기관</th>
                <th scope="col">서비스구분</th>
                <th scope="col">망종류</th>
                <th scope="col">설치년도</th>
                <th scope="col">실내외구분</th>
                <th scope="col">WIFI접속환경</th>
                <th scope="col">X좌표</th>
                <th scope="col">Y좌표</th>
                <th scope="col">작업일자</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty nearLocations}">
                <c:forEach var="item" items="${nearLocations}">
                    <tr>
                        <td>${item.distance}</td>
                        <td>${item.X_SWIFI_MGR_NO}</td>
                        <td>${item.X_SWIFI_WRDOFC}</td>
                        <td>${item.X_SWIFI_MAIN_NM}</td>
                        <td>${item.X_SWIFI_ADRES1}</td>
                        <td>${item.X_SWIFI_ADRES2}</td>
                        <td>${item.X_SWIFI_INSTL_FLOOR}</td>
                        <td>${item.X_SWIFI_INSTL_TY}</td>
                        <td>${item.X_SWIFI_INSTL_MBY}</td>
                        <td>${item.X_SWIFI_SVC_SE}</td>
                        <td>${item.X_SWIFI_CMCWR}</td>
                        <td>${item.X_SWIFI_CNSTC_YEAR}</td>
                        <td>${item.X_SWIFI_INOUT_DOOR}</td>
                        <td>${item.X_SWIFI_REMARS3}</td>
                        <td>${item.LNT}</td>
                        <td>${item.LAT}</td>
                        <td>${item.WORK_DTTM}</td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty nearLocations}">
                <tr>
                    <td colspan="17" style="text-align: center;" id="result">위치정보를 입력한 후 조회해주세요.</td>
                </tr>
            </c:if>
            </tbody>
        </table>

</c:if>

<br/>
<%--<a href="hello-servlet">Hello Servlet</a>--%>
<script>
    document.getElementById("getMyLocation").addEventListener("click", function () {
        if ("geolocation" in navigator) {
            // 현재 위치 정보를 가져올 수 있는 경우
            navigator.geolocation.getCurrentPosition(function (position) {
                const latitude = position.coords.latitude; // 위도
                const longitude = position.coords.longitude; // 경도

                document.getElementById("LAT").value = latitude;
                document.getElementById("LNT").value = longitude;
            });
        } else {
            console.log("현재 위치 정보를 가져올 수 없습니다.");
        }
    });
    function historyDelete(id){
        console.log("ㅇ", id);

        const url = `delete-history-servlet?id=`;
        console.log("aa", url);
        window.location.href = url+id;

    }

    function validateForm() {
        const latitude = document.getElementById("LAT").value;
        const longitude = document.getElementById("LNT").value;

        if (latitude.trim() === '' || longitude.trim() === '') {
            alert("빈칸을 입력해주세요.");
            return false;
        }
        return true;
    }

</script>
</div>
</body>
</html>
