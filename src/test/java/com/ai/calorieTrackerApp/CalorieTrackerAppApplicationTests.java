package com.ai.calorieTrackerApp;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class CalorieTrackerAppApplicationTests {
    /**

	@Autowired
	private geminiService service;
	@Test
	void getCompletion_HHGtTG_question() {
		String text = service.getCompletion("""
            What is the Ultimate Answer to
            the Ultimate Question of Life, the Universe,
            and Everything?
            """);
		assertNotNull(text);
		System.out.println(text);
		assertThat(text).contains("42");
	}

	@Test
	void getCompletion() {
		String text = service.getCompletion("""
            How many roads must a man walk down
            before you can call him a man?
            """);
		assertNotNull(text);
		System.out.println(text);
	}

	@Test
	void pirateCoverLetter() {
		String text = service.getCompletion("""
            Please write a cover letter for a Java developer
            applying for an AI position, written in pirate speak.
            """);
		assertNotNull(text);
		System.out.println(text);
	}


	@Test
	void writeAStory() {
		String text = service.getCompletion("Write a story about a magic backpack.");
		assertNotNull(text);
		System.out.println(text);
	}

	@Test
	void describeAnImage() throws Exception {
		String text = service.getCompletionWithImage();
		assertNotNull(text);
		System.out.println(text);
	}

	@Test
	void countItems_gemini_pro() throws Exception {
		String text = service.getCompletionWithImage();
		assertNotNull(text);
		System.out.println(text);
	}

	@Test
	void countItems_gemini_1_5() throws Exception {
		String text = service.analyzeImage(
				"""
                This is a picture of food, analyze the image and give the number of food items?

                """,
				"public.jpeg");
		assertNotNull(text);
		System.out.println(text);
	}

	@Test
	void getModels() {
		geminiRecord.ModelList models = service.getModels();
		assertNotNull(models);
		models.models().stream()
				.map(geminiRecord.Model::name)
				.filter(name -> name.contains("gemini"))
				.forEach(System.out::println);
	}



	@Test
	void countTokens_fullRequest() {
		var request = new geminiRecord.GeminiRequest(
				List.of(new geminiRecord.Content(
						List.of(new geminiRecord.TextPart("What is the airspeed velocity of an unladen swallow?")))));
		geminiRecord.GeminiCountResponse response = service.countTokens(geminiService.GEMINI_PRO, request);
		assertNotNull(response);
		System.out.println(response.totalTokens());
		assertThat(response.totalTokens()).isEqualTo(12);
	}

	@Test
	void countTokens() {
		int totalTokens = service.countTokens("What is the airspeed velocity of an unladen swallow?");
		assertThat(totalTokens).isEqualTo(12);
	}
	 */
}
