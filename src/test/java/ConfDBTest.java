import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import base.BaseServiceTest;

import com.mywork.framework.jpa.entity.ConfDB;
import com.mywork.framework.jpa.service.ConfService;


public class ConfDBTest extends BaseServiceTest{
	
	@Autowired
	private ConfService confService;
	
	@Test
	public void testFind() {
		String value = confService.findByKey("aaa");
		System.out.println(value);
	}
	
	@Test
	public void testFindAll() {
		List<ConfDB> list = confService.findAll();
		if (!CollectionUtils.isEmpty(list)) {
			list.forEach(n -> System.out.print(n));
		}
	}

}
