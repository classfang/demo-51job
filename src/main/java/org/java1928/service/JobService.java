package org.java1928.service;

import java.sql.SQLException;
import java.util.List;

import org.java1928.dao.JobDao;
import org.java1928.entity.Job;
import org.java1928.entity.PageBean;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

/**
 * 	职位信息业务类
 * @author junki
 * @date 2019年10月30日
 */
public class JobService {

	public Long init(int startPage, int endPage) {
		
		JobDao jobDao = new JobDao();
		
		Long counts = 0L;
		
		for (int i = startPage; i <= endPage; i++) {
			JXDocument doc = JXDocument.createByUrl("https://search.51job.com/list/000000,000000,0000,00,9,99,Java%25E5%25BC%2580%25E5%258F%2591%25E5%25B7%25A5%25E7%25A8%258B%25E5%25B8%2588,2," + i + ".html?lang=c&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&ord_field=0&dibiaoid=0&line=&welfare=");
			List<JXNode> jobs = doc.selN("//*[@id=\"resultList\"]/div[@class=\"el\"]");
			
			for (JXNode jxNode : jobs) {
				//System.out.println(jxNode);
//				<div class="el"> 
//				 <p class="t1 "> 
//					<em class="check" name="delivery_em" onclick="checkboxClick(this)"></em>
//					<input class="checkbox" type="checkbox" name="delivery_jobid" value="112423097" jt="0" style="display:none"> 
//					<span> 
//						<a target="_blank" title="Java开发工程师" href="https://jobs.51job.com/shenzhen-lhq/112423097.html?s=01&amp;t=0" onmousedown=""> Java开发工程师 </a> 
//					</span> 
//				</p> 
//				 <span class="t2"><a target="_blank" title="深圳市华商管理创新研究院" href="https://jobs.51job.com/all/co3999891.html">深圳市华商管理创新研究院</a></span> 
//				 <span class="t3">深圳-罗湖区</span> 
//				 <span class="t4">1-1.5万/月</span> 
//				 <span class="t5">10-30</span> 
//				</div>
				
				String name = jxNode.selOne("/p/span/a").asElement().text();
				String company = jxNode.selOne("/span[1]/a").asElement().text();
				String address = jxNode.selOne("/span[2]").asElement().text();
				String salary = jxNode.selOne("/span[3]").asElement().text();
				String date = jxNode.selOne("/span[4]").asElement().text();
				
				Job job = Job.builder().name(name).company(company).address(address).salary(salary).date(date).build();
				
				// System.out.println(job);
				
				int result = 0;
				try {
					result = jobDao.insert(job);
				} catch (SQLException e) {
					e.printStackTrace();
					continue;
				}
				counts += result;
				
			}
			System.out.println("第"+ i +"页爬取完毕！");
		}
		
		return counts;
	}

	public List<Job> query() throws SQLException {

		JobDao jobDao = new JobDao();

		return jobDao.select();

	}

	public PageBean<Job> page(Long currentPage, Long pageSize) throws SQLException {

		JobDao jobDao = new JobDao();

		Long totalRows = jobDao.count();

		PageBean<Job> page = new PageBean<>(currentPage, pageSize, totalRows);

		List<Job> jobs = jobDao.selectPage(page.getStartIndex(), page.getPageSize());

		page.setDataList(jobs);

		return page;

	}
}
