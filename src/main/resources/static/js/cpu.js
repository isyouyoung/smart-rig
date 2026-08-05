$(document).ready(function () {

    console.log("CPU 페이지 JS 실행");



    $.ajax({

        url: "/model/v1/getModelListByItemType",
        type: "GET",
        data: {
            itemType: "CPU"
        },
        dataType: "JSON"

    }).then(

        function(list) {

            console.log(list);

            let html = "";

            list.forEach(function(cpu) {

                html +=
                    "<div class='cpu-item'>"
                    + cpu.modelName
                    + "</div>";

            });

            $("#cpuList").html(html);

        },

        function(xhr) {

            console.log(xhr);

        }

    );

});