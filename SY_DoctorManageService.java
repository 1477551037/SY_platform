package sy.doctor.service;

import java.util.List;

import javax.annotation.Resource;

import sy.doctor.dao.QZ_dInterrogationRecordDao;
import sy.doctor.dao.SY_DoctorDao;
import sy.doctor.dao.SY_DoctorManageDao;
import sy.doctor.entity.Pager;
import sy.doctor.entity.SY_tInterrogationRecord;
import sy.doctor.entity.SY_tWithdrawalsInfo;
import sy.doctor.entity.YaopinItem;
import sy.shop.entity.SY_tDoctor;
import sy.shop.entity.SY_tPageRecord;
import sy.shop.entity.SY_tPrescription;

public class SY_DoctorManageService {
	private SY_DoctorManageDao doctorManageDao;
	
	@Resource(name="QZ_dInterrogationRecordDaoImpl")
	private QZ_dInterrogationRecordDao interDao;
	
	@Resource(name="doctorDao")
	private SY_DoctorDao doctorDao;
	
	public void setDoctorManageDao(SY_DoctorManageDao doctorManageDao) {
		this.doctorManageDao = doctorManageDao;
	}


	public List<SY_tPrescription> queryAllPre(){
		return doctorManageDao.queryAllPre();
	}
	
	public SY_tWithdrawalsInfo getWInfoByDId(int doctorId){
		return doctorManageDao.getWInfoByDId(doctorId);
	}
	
	public void updateWInFo(SY_tWithdrawalsInfo wInfo){
		this.doctorManageDao.updateWInFo(wInfo);
	}
	
	
	public Pager<SY_tInterrogationRecord> getWithdrawals(SY_tInterrogationRecord record, int pageNum, int pageSize){
		return doctorManageDao.getWithdrawals(record, pageNum, pageSize);
	}
	
	public Pager<SY_tInterrogationRecord> getCompleteInterrogation(SY_tInterrogationRecord record, int pageNum, int pageSize){
		return interDao.selectHistoryPre(record, pageNum, pageSize);
	}
	
	public Pager<SY_tPageRecord> getPageList(SY_tPageRecord searchPage, int pageNum, int pageSize){
		return doctorManageDao.getPageList(searchPage, pageNum, pageSize);
	}
	/**
	 * 根据一张处方的id以及医生的信息计算出得到医生可得的金额
	 * 医生D可得金额=（药品E让利+药品F让利）*（医生提点）/(医生最高提点+平台扣率)
	 * 药品让利=让利点*药品价格
	 * */
	public Double getPriceByPid(SY_tDoctor doctor,Integer presId){
		Double price =0.0;   //总价格
		
		//得到药品
		List<YaopinItem> yinPinList = doctorManageDao.getYinPinItemByPid(presId);
		
		double doctorCommission = 0;//医生提成
		
		double yaoPinCommission = 0;//单个药品的提成
		
		double platformCommission = 0;//平台提成
		
		double yaoPinPrice = 0;//单个药品的价格
		
		for(YaopinItem yaoPin:yinPinList){
			price +=yaoPinCommission*yaoPinPrice*yaoPin.getCount();
		}
		
		price= price*(doctorCommission/(doctorCommission+platformCommission));
		return price;
	}


	public boolean updateDoctorPassWord(SY_tDoctor doctor,String oldPossword, String newPassword) {
		doctor.setSY_password(newPassword);
		doctorDao.updata(doctor);
		return true;
	}
}
