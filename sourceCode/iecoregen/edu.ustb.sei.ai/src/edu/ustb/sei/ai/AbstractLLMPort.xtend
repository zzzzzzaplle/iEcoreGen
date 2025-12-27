package edu.ustb.sei.ai

import java.util.ArrayList
import java.util.List

abstract class AbstractLLMPort<REQ,RES> implements LLMPort{
	protected val String url
	protected val String model
	protected val int timeout
	protected val String apiKey
	protected val String systemPrompt
	protected val int maxInputTokens
	protected val int maxOutputTokens
	
	new(String apiKey, Options options) {
		this.url = options.url
		this.model = options.model
		this.timeout = options.timeout
		this.apiKey = apiKey
		this.systemPrompt = options.systemPrompt
		this.maxInputTokens = options.maxInputTokens
		this.maxOutputTokens = options.maxOutputTokens
	}
	
	override List<String> queryStrings(String prompt) {
		try {
			val request = prompt.buildRequest
			val response = request.doRequest
			response.handleResponse
			return new ArrayList(response.choices.map[it.extractOutput])
			
		} catch(Exception e) {
			return #[]
		}
	}
	
	override String queryString(String prompt) {
		try {
			val request = prompt.buildRequest
			val response = request.doRequest
			response.handleResponse
			return response.choices.get(0).extractOutput
			
		} catch(Exception e) {
			return ""
		}
	}
	
	
	protected def RES doRequest(REQ request)
	
	protected def List<String> getChoices(RES response)
	
	protected def REQ buildRequest(String prompt)
	
	protected def void handleResponse(RES response) {}
	
	protected def String extractOutput(String output) {output}
}