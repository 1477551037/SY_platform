package sy.doctor.service;

import java.util.List;

public interface SY_DoctorInterrogationRecordService {
	public void settlementRecordList(List<String> recordList);
	public void settlementRecord(String orderNum);
	public void cashdividendsRecordList(List<String> recordList);
	public void cashdividendsRecord(String orderNum);
}
