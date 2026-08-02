$(document).ready(function () {

    // 로그인 남은 시간 표시 (임시로 5분만 카운트하는 하드코딩임)
    let remainTime = 300;

    function updateTokenTime() {

        let min = Math.floor(remainTime / 60);
        let sec = remainTime % 60;

        $("#tokenTime").text(
            "토큰 남은 시간 : "
            + min + "분 "
            + sec + "초"
        );

    }

    updateTokenTime();

    let tokenTimer = setInterval(function () {

        remainTime--;

        updateTokenTime();

        if (remainTime <= 0) {

            clearInterval(tokenTimer);

            $("#tokenTime").text(
                "토큰 시간이 만료되었습니다."
            );

            return;

        }

    }, 1000);


    // 재고 조회 버튼
    $("#btnSearchStock").on("click", function () {

        console.log("조회 버튼 클릭");

        let modelId = $("#modelId").val();


        // Model ID 입력 여부 확인 (빈칸 시 안내 메시지 출력)
        if (modelId === "") {

            alert("Model ID를 입력하세요.");

            $("#modelId").focus();

            return;
        }

        console.log("입력한 Model ID : " + modelId);


        $.ajax({

            url: "/stock/v1/getStockByModelId",
            type: "GET",
            data: {
                modelId: modelId
            },
            dataType: "JSON"

        }).then(
            function (json) {

                console.log(json);

                $("#stockQuantity").text(
                    "수량 : " + json.quantity
                );

                $.ajax({

                    url: "/model/v1/getModelById",
                    type: "GET",
                    data: {
                        modelId: json.modelId
                    },
                    dataType: "JSON"

                }).then(
                    function (model) {

                        console.log(model);

                        $("#stockModel").text(
                            "Model : " + model.modelName
                        );

                    },

                    function (xhr) {

                        console.log(xhr);

                        $("#stockModel").text(
                            "Model : (모델명 조회 실패)"
                        );

                    }
                );

            },
            function (xhr) {

                alert(xhr.responseJSON.message);

                $("#stockModel").text("");
                $("#stockQuantity").text("");

            });

    });


    // 메인 화면 이동
    $("#btnHome").on("click", function () {

        location.href = "/html/index.html";

    });

    $("#btnCpu").on("click", function () {

        $("#cpuModal").show();

        $("#modalTitle").text("💻 CPU > 제조사 선택");

        $("#modalContent").html(
            "<button>Intel</button>" +
            "<button>AMD</button>"
        );

    });


    $("#btnCloseModal").on("click", function () {

        $("#cpuModal").hide();

    });

});