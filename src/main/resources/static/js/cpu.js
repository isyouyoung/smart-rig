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

        $("#cpuTitle").text("CPU 목록 (" + list.length + ")");

        let seriesList = {
            "Celeron": [],
            "Pentium": [],
            "i3": [],
            "i5": [],
            "i7": [],
            "i9": []
        };

        if (!list || list.length === 0) {
            html = "<div class='cpu-item'>등록된 CPU가 없습니다.</div>";
        } else {
            list.forEach(function (cpu) {

                seriesList[cpu.series].push(cpu);

            });

            console.log("시리즈별 분류:", seriesList);

            Object.keys(seriesList).forEach(function(series) {

                if (seriesList[series].length === 0) {
                    return;
                }

                html += "<div class='cpu-series'>";

                html += "<h3>" + series + " (" + seriesList[series].length + ")</h3>";

                seriesList[series].forEach(function(cpu) {

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
                        + "<div class='cpu-model'>" + cpu.modelName + "</div>"
                        + "<div class='cpu-right'>"
                        + "<div class='" + stockClass + "'>" + stockText + "</div>"
                        + "<button type='button' class='stock-in-btn' data-model-id='" + cpu.modelId + "'>입고</button>"
                        + "</div>"
                        + "</div>";

                });

                html += "</div>";

            });

        }

        $("#cpuList").html(html);
    }

    // ==========================================
    // 2. 이벤트 바인딩 영역
    // ==========================================
    $(".generation-btn").on("click", function () {
        selectedGeneration = $(this).data("generation");
        $(".generation-btn").removeClass("active");
        $(this).addClass("active");
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

    // 입고 버튼: 수량을 묻고 입고 API를 호출한 뒤 현재 목록을 새로 불러온다.
    $("#cpuList").on("click", ".stock-in-btn", function () {
        const $btn = $(this);
        const modelId = $btn.data("model-id");
        const modelName = $btn.closest(".cpu-item").find(".cpu-model").text();

        const input = prompt(modelName + "\n입고 수량을 입력하세요.", "1");
        if (input === null) {
            return;   // 취소를 누른 경우
        }

        const quantity = Number(input);
        if (!Number.isInteger(quantity) || quantity < 1) {
            alert("입고 수량은 1 이상의 정수로 입력해 주세요.");
            return;
        }

        $btn.prop("disabled", true);   // 두 번 눌러 중복 입고되는 걸 막는다.

        $.ajax({
            url: "/inventory/v1/stockIn",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ modelId: modelId, quantity: quantity }),
            success: function () {
                getCpuList();   // 선택한 세대 그대로 재고를 다시 불러온다.
            },
            error: function (xhr) {
                if (xhr.status === 401 || xhr.status === 403) {
                    alert("로그인이 필요합니다. 로그인해 주세요.");
                } else if (xhr.responseJSON && xhr.responseJSON.message) {
                    alert(xhr.responseJSON.message);
                } else {
                    alert("입고에 실패했습니다. (" + xhr.status + ")");
                }
            },
            complete: function () {
                $btn.prop("disabled", false);
            }
        });
    });

    getCpuList(); // 페이지 진입 시 전체 목록 최초 로드
});