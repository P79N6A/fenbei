<?php
header("content-type:text/html; charset=utf-8");
$cnn=mysqli_connect("localhost","a0118222815","961a3bda","a0118222815");//此处修改成你的数据库


function randomkeys($length) {
    $returnStr='';
    $pattern = '1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLOMNOPQRSTUVWXYZ';
    for($i = 0; $i < $length; $i ++) {
        $returnStr .= $pattern {mt_rand ( 0, 61 )}; //生成php随机数
    }
    return $returnStr;
}
   $suiji = randomkeys(6);

	//获取表单提交数据
	$a=$_POST['title'];
	$b=$_POST['content'];
	$c=$_POST['kefu'];
	  if ( $a == ""){ 
  			$a = '110'; 
  		}

  			  if ( $b == ""){ 
  			$b = '110'; 
  		} 

  		  			  if ( $c == ""){ 
  			$c = '110'; 
  		} 
	//构建要查询的SQL语句
	$showtime=date("Y-m-d H:i:s");
	$bm="set names utf8";
	//设置数据库编码为utf8
	mysqli_query($cnn,$bm);
	/*$sql = "INSERT INTO yyyg.ls_cart (`id`, `pid`, `uid`, `amount`, `price`, `specs`, `add_time`) VALUES (NULL, '$pid', '$uid', '$amount', '$price', '0', '$add_time');";*/
	$sql = "INSERT INTO `a0118222815`.`cdat` (`id`, `cd`, `localt`, `snosat`, `rnorec`, `kefu`) VALUES (NULL, '$a', '$b', '$showtime', '$suiji', '$c');";

	//执行SQL语句
	$ccc=mysqli_query($cnn,$sql);
	if($ccc)
	{
		if($a =='110'){
			echo "<script> alert('请认真对待,谢谢合作,不然就别用了');history.go(-1); </script>";
		}else{
			echo "<script> alert('恭喜您，申请成功，在APP端填入专属码即可获得专属APP:您的专属码为: $suiji(请先记录下此专属码,该码只显示一次,多次提交永久不予通过)');history.go(-1); </script>";
		}
		
	}else {
		echo "<script> alert('失败了，老铁,在试试吧');history.go(-1); </script>";
	}



?>