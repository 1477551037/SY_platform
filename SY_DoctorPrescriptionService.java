package sy.doctor.service;

import sy.doctor.entity.Pager;
import sy.doctor.entity.SY_tDoctorWithdrawals;
import sy.doctor.entity.SY_tInterrogationRecord;
import sy.doctor.entity.SY_tcommPrescription;
import sy.shop.entity.SY_tDoctor;

public interface QZ_dPrescriptionService {
	/**
	 * 新增一张常用处方 
	 * @param xstr  存储西药数据的字符串
	 * @param zstr  存储中药数据的字符串
	 * @param sy_tDoctor 开方医生
	 * @return 处方编号
	 * */
	public String addPrescription(String preName,String desc ,String xstr, String zstr,SY_tDoctor sy_tDoctor,String isComm);
	
	/**
	 * 更新处方信息
	 * @param xstr  存储西药数据的字符串
	 * @param zstr  存储中药数据的字符串
	 * @param sy_tDoctor 开方医生
	 * @param RecordId 处方编号
	 * @return 处方编号
	 * */
	public String updatePrescription(String xstr, String zstr,SY_tDoctor sy_tDoctor,String RecordId);
	public void updatePreByRecordId(String prescriptionDesc, String remarks, String recordId);
	/**
	 * 删除常用处方
	 * @param RecordId 处方编号
	 * */
	public boolean delectPrescription(String RecordId);
	
	
	/**
	 * @param searchPres
	 *            存放查询条件
	 * @param pageNum
	 *            当前页数
	 * @param pageSize
	 */
	public Pager<SY_tcommPrescription> selectCommPres(SY_tcommPrescription searchPres, int pageNum, int pageSize);

	public Pager<SY_tInterrogationRecord> selectHistoryPre(SY_tInterrogationRecord searchRecord ,int pageNum,int pageSize);
	
}
