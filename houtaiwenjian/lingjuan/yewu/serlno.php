<?php
$cnn=mysqli_connect("localhost","a0118222815","961a3bda","a0118222815");//此处修改成你的数据库
$bm="set names utf8";
		mysqli_query($cnn,$bm);//设置数据库编码为utf8
			//$sql="SELECT * FROM  ff_vod order by vod_id desc";//查询用户名表
			
			$hui = "SELECT count(*) FROM huitao";
				// echo mysqli_query($cnn,$hui);
				$bar = 1;
class user{
	public $name;
	public $cate_id;

	
	function __construct($name,$cate_id){
		$this->name = $name;
		$this->cate_id = $cate_id;
	}
}
//分页数
$name= $_GET["name"];
//$sql = "SELECT * FROM is_product order by sort desc LIMIT 0,30";//
//$sql = "SELECT * FROM `ls_product` order by sort LIMIT $qishi,$path";
$is= "ab";
$sql = "SELECT * FROM `cdat` WHERE `rnorec` LIKE '$name'";


//echo $sql;


//$sql = "SELECT * FROM `ls_product` LIMIT 0, 30 ";
//$sql = "SELECT * FROM ff_vod WHERE vod_inputer LIKE 'xml_1133' ORDER BY vod_id desc LIMIT $qishi,$path";
		
//$sql = "SELECT * FROM ff_vod LIMIT $qishi,$path";	
$sqld="SELECT COUNT(*) AS NumberOfOrders FROM `cdat` where rnorec like '$name'";

$cccd=mysqli_query($cnn,$sqld);//执行代码
			while($axx = mysqli_fetch_assoc($cccd))
			{
				$jilu=$axx['NumberOfOrders'];
			//echo $jilu;
				}
				//echo $jilu;

		echo "[";
		$ccc=mysqli_query($cnn,$sql);//执行代码
		//遍历一下数据库内容
		while($arr = mysqli_fetch_assoc($ccc)){
			

			//自增加
			$i = $bar++;
			//echo $i;
			$id = $arr["kefu"];	
				  if ($id == ""){ 
  						$id = '110'; 
  					}
			$name = $arr["cd"];	
							  if ( $name == ""){ 
  						$name = '110'; 
  					}
			$obj = new user($name,$id);
			//已JSON的形式返回给客户端
			echo json_encode($obj);	
			//判断是否为最后一个 如果是最后一个的话加入,号s
			if($i!=$jilu){
				echo ",";
			}
		}


				  if ($id == ""){ 
  						$id = '{"name":"110","cate_id":"110"}'; 
  						echo $id."]";
  					}else {
  						echo "]";
  					}
		


?>    
