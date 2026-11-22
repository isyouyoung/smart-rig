$(document).ready(function () {
    console.log("메인보드 페이지 JS 실행");

    let selectedSocket = "LGA1151";

    // 화면에 문자열을 넣기 전에 특수문자를 안전하게 바꾼다.
    function esc(text) {
        return $("<div>").text(text == null ? "" : text).html();
    }

    // [함수 1] AJAX 데이터 통신 전담 함수
    function getBoardList() {
        $.ajax({
            url: "/model/v1/getBoardListWithStockBySocket?socket=" + encodeURIComponent(selectedSocket),
            type: "GET",
            dataType: "JSON"
        }).then(
            function (list) {
                console.log("수신 데이터:", list);
                renderBoardList(list);
            },
            function (xhr) {
                console.log("조회 에러:", xhr);
                $("#boardList").html("<div class='board-empty'>목록을 불러오지 못했습니다. 로그인 상태를 확인해 주세요.</div>");
            }
        );
    }

    // [함수 2] 화면 렌더링 전담 함수
    function renderBoardList(list) {
        let html = "";

        $("#boardTitle").text(selectedSocket + " 메인보드 (" + list.length + ")");

        if (!list || list.length === 0) {
            html = "<div class='board-empty'>이 소켓에 등록된 메인보드가 없습니다.</div>";
        } else {
            // series(칩셋: H110, B250 등)별로 묶는다.
            let groups = {};
            list.forEach(function (board) {
                let key = board.series || "기타";
                if (!groups[key]) {
                    groups[key] = [];
                }
                groups[key].push(board);
            });

            Object.keys(groups).sort().forEach(function (series) {
                html += "<section class='board-group'>";
                html += "<h3>" + esc(series) + "<span class='count'>" + groups[series].length + "개 모델</span></h3>";

                groups[series].forEach(function (board) {
                    let stockText = "";
                    let stockClass = "";

                    if (board.quantity === null) {
                        stockText = "재고 미등록";
                        stockClass = "stock-null";
                    } else if (board.quantity === 0) {
                        stockText = "품절";
                        stockClass = "stock-zero";
                    } else {
                        stockText = "재고 " + board.quantity + "개";
                        stockClass = "stock-ok";
                    }

                    html +=
                        "<div class='board-item'>"
                        + "<div class='board-model'>" + esc(board.modelName) + "</div>"
                        + "<div class='board-right'>"
                        + "<div class='" + stockClass + "'>" + stockText + "</div>"
                        + "<button type='button' class='stock-in-btn' data-model-id='" + board.modelId + "'>입고</button>"
                        + "</div>"
                        + "</div>";
                });

                html += "</section>";
            });
        }

        $("#boardList").html(html);
    }

    // 소켓 버튼 클릭 시 선택 표시를 바꾸고 목록을 다시 조회한다.
    $(".socket-btn").on("click", function () {
        selectedSocket = $(this).data("socket");
        $(".socket-btn").removeClass("active");
        $(this).addClass("active");
        getBoardList();
    });

    // 입고 버튼: 수량을 묻고 입고 API를 호출한 뒤 목록을 새로 불러온다.
    $("#boardList").on("click", ".stock-in-btn", function () {
        const $btn = $(this);
        const modelId = $btn.data("model-id");
        const modelName = $btn.closest(".board-item").find(".board-model").text();

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
                getBoardList();   // 늘어난 재고 반영
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

    // 페이지 진입 시 기본 소켓(LGA1151) 목록을 불러온다.
    getBoardList();
});
