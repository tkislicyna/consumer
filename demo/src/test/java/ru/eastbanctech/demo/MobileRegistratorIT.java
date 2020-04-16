package ru.eastbanctech.demo;


import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubRunnerOptions;
import org.springframework.cloud.contract.stubrunner.StubRunnerOptionsBuilder;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author t.kozlova
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MobileRegistratorIT {

	private StubRunnerOptions options =
			new StubRunnerOptionsBuilder()
					.withStubs("ru.eastbanctech:hrdt-ms-mobile:1.0")
					.withStubsMode(StubRunnerProperties.StubsMode.REMOTE)
					.withStubRepositoryRoot(
							"git://https://github.com/tkislicyna/contracts.git")
					.withGenerateStubs(true)
					.build();

	@Rule
	public StubRunnerRule rule = new StubRunnerRule().options(options);

	@Test
	public void should_generate_a_stub_at_runtime() throws Exception {
		int port = this.rule.findStubUrl("hrdt-ms-mobile").getPort();

		String object = new RestTemplate().getForObject("http://localhost:" + port + "/health", String.class);

		BDDAssertions.then(object).isEqualTo("OK");
	}
}
