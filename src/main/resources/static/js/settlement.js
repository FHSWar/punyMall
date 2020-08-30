$(function(){
    //计算总价
    var array = $(".qprice");
    var totalCost = 0;
    for(var i = 0;i < array.length;i++){
        var val = parseInt($(".qprice").eq(i).html().substring(1));
        totalCost += val;
    }
    $("#totalprice").html("￥"+totalCost);
    //settlement2使用
    $("#settlement2_totalCost").val(totalCost);
});

//商品数量++
function addQuantity(obj){
    var index = $(".car_btn_2").index(obj);
    var stock = parseInt($(".productStock").eq(index).val());
    var price = parseInt($(".productPrice").eq(index).val());
    var inputObj = $(".car_ipt").eq(index);
    var quantity = inputObj.val();
    var id = $(".id").eq(index).val();
    ++quantity;
    if(quantity > stock){
        quantity = stock;
        alert("库存不足！");
    }
    var cost = quantity*price;
    var productId = $(".productId").eq(index).val();
    $.ajax({
        url:"/cart/updateCart/add/"+id+"/"+productId+"/"+quantity+"/"+cost,
        type:"POST",
        dataType:"text",
        success:function (data) {
            if(data == "success"){
                //更新toSettlement的数据
                $(".qprice").eq(index).html("￥"+cost);
                inputObj.val(quantity);
                if(quantity < stock){
                    var totalCost = parseInt($("#totalprice").html().substring(1));
                    totalCost += price;
                    $("#totalprice").html("￥"+totalCost);
                }
                //更新searchBar的数据
                $(".quantity").eq(index).text(quantity);
                $(".cost").eq(index).text(cost);
                var searchBarTotalCost = parseInt($("#totalCost").html().substring(1));
                searchBarTotalCost += price;
                $("#totalCost").html("￥"+searchBarTotalCost);
            }
        }
    });
}

//商品数量--
function subQuantity(obj){
    var index = $(".car_btn_1").index(obj);
    //获取价格
    var price = parseInt($(".productPrice").eq(index).val());
    var inputObj = $(".car_ipt").eq(index);
    //获取当前个数
    var quantity = inputObj.val();
    var id = $(".id").eq(index).val();
    --quantity;
    if(quantity == 0){
        quantity = 1;
    }
    var cost = quantity*price;
    var productId = $(".productId").eq(index).val();
    $.ajax({
        url:"/cart/updateCart/sub/"+id+"/"+productId+"/"+quantity+"/"+cost,
        type:"POST",
        dataType:"text",
        success:function(data){
            if(data == "success"){
                $(".qprice").eq(index).html("￥"+cost);
                inputObj.val(quantity);
                var totalCost = parseInt($("#totalprice").html().substring(1));

                if(quantity >= 1){
                    totalCost -= price;
                }
                if(quantity == 1){
                    alert("不能再减啦！");
                }
                $("#totalprice").html("￥"+totalCost);

                // if(quantity!=1){
                //     var totalCost = parseInt($("#totalprice").html().substring(1));
                //     totalCost -= price;
                //     $("#totalprice").html("￥"+totalCost);
                // }
                $(".quantity").eq(index).text(quantity);
                $(".cost").eq(index).text(cost);
                var searchBarTotalCost = parseInt($("#totalCost").html().substring(1));
                searchBarTotalCost -= price;
                $("#totalCost").html("￥"+searchBarTotalCost);
            }
        }
    });
}

//移出购物车
function removeCart(obj){
    var index = $(".delete").index(obj);
    var id = $(".id").eq(index).val();
    if(confirm("是否确定要删除？")){
        window.location.href = "/cart/removeCart/"+id;
    }
}