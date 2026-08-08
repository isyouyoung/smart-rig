$(document).ready(function () {
    console.log("CPU 페이지 JS 실행");

    let selectedGeneration = "ALL";
    let url = "/model/v1/getCpuListWithStock";

    // ==========================================
    // 1. 함수 정의 영역 (호이스팅에 의존하지 않는 명확한 선언부)
    // ==========================================

    // [함수 1] AJAX 데이터 통신 전담 함수
    function getCpuList() {
        $.ajax({
            url: url,
            type: "GET",
            dataType: "JSON"
        }).then(
            function (list) {
                console.log("수신 데이터:", list);
                renderCpuList(list); // 렌더링 전담 함수 호출
            },
            function (xhr) {
                console.log("조회 에러:", xhr);
                $("#cpuList").html("<div class='cpu-item'>목록을 불러오는 중 오류가 발생했습니다.</div>");
            }
        );
    }

    // [함수 2] 화면 렌더링 전담 함수 (책임 분리)
    function renderCpuList(list) {
        let html = "";

        if (!list || list.length === 0) {
            html = "<div class='cpu-item'>등록된 CPU가 없습니다.</div>";
        } else {
            list.forEach(function (cpu) {
                let stockText = "";
                let stockClass = "";

                if (cpu.quantity === null) {
                    stockText = "재고 미등록";
                    stockClass = "stock-null";
                } else if (cpu.quantity === 0) {
                    stockText = "품절";
                    stockClass = "stock-zero";
                } else {
                    stockText = "재고 : " + cpu.quantity + "개";
                    stockClass = "stock-ok";
                }

                html +=
                    "<div class='cpu-item'>"
                    + cpu.modelName
                    + " / <span class='" + stockClass + "'>" + stockText + "</span>"
                    + "</div>";
            });
        }

        $("#cpuList").html(html);
    }

    // ==========================================
    // 2. 이벤트 바인딩 영역
    // ==========================================
    $(".generation-btn").on("click", function () {
        selectedGeneration = $(this).data("generation");
        console.log("선택 세대 : " + selectedGeneration);

        if (selectedGeneration === "ALL") {
            url = "/model/v1/getCpuListWithStock";
        } else {
            url = "/model/v1/getCpuListWithStockByGeneration?generation=" + selectedGeneration;
        }

        getCpuList(); // 세대 변경 후 API 재호출
    });

    // ==========================================
    // 3. 페이지 초기 실행 영역
    // ==========================================
    getCpuList(); // 페이지 진입 시 전체 목록 최초 로드
});