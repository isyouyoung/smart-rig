$(document).ready(function () {

    console.log("CPU 페이지 JS 실행");



    $.ajax({

        url: "/model/v1/getCpuListWithStock",
        type: "GET",
        dataType: "JSON"

    }).then(

        function(list) {

            console.log(list);

            let html = "";

            list.forEach(function(cpu) {

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

            $("#cpuList").html(html);

        },

        function(xhr) {

            console.log(xhr);

        }

    );

});