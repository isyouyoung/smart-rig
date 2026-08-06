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

                html +=
                    "<div class='cpu-item'>"
                    + cpu.modelName
                    + " / 재고 : "
                    + cpu.quantity
                    + "</div>";

            });

            $("#cpuList").html(html);

        },

        function(xhr) {

            console.log(xhr);

        }

    );

});