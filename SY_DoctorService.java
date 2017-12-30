package sy.doctor.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import sy.doctor.dao.QZ_DoctorWithdrawlasDao;
import sy.doctor.dao.SY_DoctorDao;
import sy.doctor.entity.Pager;
import sy.doctor.entity.SY_tDoctorInfo;
import sy.doctor.entity.SY_tDoctorWithdrawals;
import sy.shop.entity.SY_tDataBase;
import sy.shop.entity.SY_tDoctor;
import sy.user.dao.MyException;


public class SY_DoctorService {
	private SY_DoctorDao doctorDao;
	
	@Resource(name="QZ_DoctorWithdrawlasDaoImpl")
	private QZ_DoctorWithdrawlasDao wDao;
	
	public void setDoctorDao(SY_DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}
	 @Deprecated
	public SY_tDoctor login(SY_tDoctor doctor){
		return doctorDao.login(doctor);
	}
	
	/**
	 * 进行登录
	 */
	public SY_tDoctor login(String username,String password) throws MyException{
		SY_tDoctor sy_tDoctor=doctorDao.sy_queryDoctor(username);
		if(sy_tDoctor==null) throw new MyException("此用户不存在！");
		SY_tDoctor sy_tdoctor=doctorDao.sy_login(username,password);
		if(sy_tdoctor==null) throw new MyException("密码错误！");
        doctorDao.SY_EditStatus(sy_tdoctor.getSY_doctorId());  
		//修改医生状态
		
        //if(!sy_tUser.isSY_status()) throw new MyException("请登录邮箱进行激活！");
		return sy_tdoctor;
	}

	
	/**
	 * @param searchYaoPin 存放查询条件
	 * @param pageNum 当前页数
	 * @param pageSize 一页有多少条记录
	 * */
	public Pager<SY_tDataBase> yaoPinFenYe(SY_tDataBase searchYaoPin,int pageNum,int pageSize){
		return doctorDao.yaoPinFenYe(searchYaoPin, pageNum, pageSize);
	}
	
	public Pager<SY_tDoctorWithdrawals> getWithdrawals(SY_tDoctorWithdrawals withdrawals, int pageNum, int pageSize){
		return doctorDao.getWithdrawals(withdrawals, pageNum, pageSize);
	}
	public SY_tDoctor queryDoctor(String sy_name) {
		return doctorDao.sy_queryDoctor(sy_name);
	}
	public SY_tDoctor SY_queryPhoneExist(String sy_phoneNum) {
		return doctorDao.sy_queryPhoneExist(sy_phoneNum);
	}
	public void SY_registDoctor(SY_tDoctor doctor) {
		doctor.setDepartment(null);
		doctor.setSY_byDepartment(null);
		doctor.setSY_byHospital(null);
		doctor.setSY_illLabel(null);
		doctor.setSY_pdetail(null);
		doctor.setSY_photo("/web/doctor/assets/img/user2.png");
		doctor.setSY_renzheng(0);
		doctor.setSY_skil(null);
		doctor.setSY_status(true);
		doctor.setSY_zhengshu(null);
		doctorDao.SY_registDoctor(doctor);
		SY_tDoctorInfo info=new SY_tDoctorInfo();
		info.setDoctorID(doctor);
		info.setRemind(new BigDecimal("0.15"));
		doctorDao.saveDoctorInfo(info);
		SY_tDoctorWithdrawals doctorWithdrawals=new SY_tDoctorWithdrawals();
		doctorWithdrawals.setdId(doctor.getSY_doctorId());
		wDao.insertWithdrawals(doctorWithdrawals);
	}
	/**
	 * 医生提交的审核信息
	 * */
	public void SY_shenHe(String dName,SY_tDoctor doctor){
		SY_tDoctor updateDoctor=doctorDao.findByDoctorname(dName);
		updateDoctor.setSY_byHospital(doctor.getSY_byHospital());
		updateDoctor.setSY_byDepartment(doctor.getSY_byDepartment());
		updateDoctor.setSY_title(doctor.getSY_title());
		updateDoctor.setIDCard(doctor.getIDCard());
		doctorDao.updata(updateDoctor);
	}
	
	public List<SY_tDoctor> isNoShenHeDoctor(){
		return doctorDao.isNoShenHeDoctor();
	}
	
	/**
	 * 启用医生
	 * */
	public void pass(int doctorId){
		SY_tDoctor updateDoctor=doctorDao.get(doctorId);
		updateDoctor.setSY_renzheng(1);
		doctorDao.updata(updateDoctor);
	}
	
	public SY_tDoctor getDoctorById(int doctorId){
		SY_tDoctor doctor=doctorDao.get(doctorId);
		return doctor;
	}
	
	/**
	 * 将医生上传的图片信息存入
	 * */
	public void saveImgInfo(SY_tDoctor doctor,String imgUrl){
		
	}
	
	/**
	 * 将医生上传的头像信息存入
	 * */
	public void saveHeadPhoto(SY_tDoctor doctor,String imgUrl){
		SY_tDoctor updateDoctor=doctorDao.get(doctor.getSY_doctorId());
		updateDoctor.setSY_photo(imgUrl);
		doctorDao.updata(updateDoctor);
	}
	public void saveProveImg(SY_tDoctor doctor, String ImgInfo,String imgUrl) {
		/*
		SY_tDoctor updateDoctor=doctorDao.get(doctor.getSY_doctorId());
		List<String> list = JsonUtil.toObjList(updateDoctor.getSY_zhengshu());
		
		doctorDao.updata(updateDoctor);*/
		SY_tDoctorInfo updateDoctorInfo = doctorDao.selectDoctorInfoById(doctor.getSY_doctorId());
		HashMap<String, String> proveImg = updateDoctorInfo.getProveImg();
		proveImg.put(ImgInfo, imgUrl);
		doctorDao.updateDoctorInfo(updateDoctorInfo);
	}
}
