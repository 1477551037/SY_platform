package sy.doctor.service;

import java.math.BigDecimal;
import java.util.List;

import sy.doctor.entity.Pager;
import sy.doctor.entity.SY_tDoctorWithdrawals;
import sy.doctor.entity.SY_tInterrogationRecord;

public interface SY_DoctorWithdrawlasService {

	public BigDecimal withdrawlasPriceByIdList(List<String> idArr);
	public BigDecimal withdrawlasPriceById(String id);
	public boolean insertWithdrawals(SY_tDoctorWithdrawals doctorWithdrawals);
	public Pager<SY_tDoctorWithdrawals> getWithdrawals(SY_tDoctorWithdrawals record, int pageNum, int i);
	public boolean cashChecking(String dwId,boolean isPass);
	public boolean cashDividends(String dwId);
	
}
