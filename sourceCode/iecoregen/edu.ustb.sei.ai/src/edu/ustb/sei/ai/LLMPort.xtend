package edu.ustb.sei.ai

import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.regex.Pattern

interface LLMPort {
	def List<String> queryStrings(String prompt)
	def String queryString(String prompt)
	
	val regexCache = <String, Pattern>newHashMap
	
	def Pattern buildPattern(String regex) {
		return regexCache.computeIfAbsent(regex)[
			Pattern.compile(regex)
		]
	}
	
	static class ExtractionResult extends HashMap<String, List<String>> {
		var String currentTag;
		
		def void setCurrentTag(String tag) {
			this.currentTag = tag
		}
		
		def void addResult(String result) {
			this.compute(currentTag)[key, list|
				if(list===null) {
					newArrayList(result)
				} else {
					list.add(result)
					list
				}
			]
		}
	}
	
	def Map<String, List<String>> extractOutputFromBlocks(String output, List<String> tags) {
		val result = new ExtractionResult
		
		output.lines.reduce(null) [prefix, line|
			if(prefix === null) { // no in 
				for(tag : tags) {
					val begPat = '''\s*```«tag»'''.toString.buildPattern
					
					if(begPat.asMatchPredicate.test(line)) {
						result.currentTag = tag
						return ""
					}
				}
				return null
			} else {
				val endPat = "\\s*```".buildPattern
				
				if(endPat.asMatchPredicate.test(line)) {
					// record
					result.addResult(prefix)
					return null
				} else {
					val content = if(prefix.isEmpty) line else prefix + "\n" + line
					return content
				}
			}
		]
		
		return result
	}
	
	static class Options {
		protected var String url = "https://api.deepseek.com"
		protected var String model = "deepseek-chat"
		protected var int timeout = 600
		protected var String systemPrompt = null
		protected var int maxInputTokens = 1<<16
		protected var int maxOutputTokens = 1<<15
		
		def Options setUrl(String url) {
			this.url = url
			return this
		}
		
		def Options setModel(String model) {
			this.model = model
			return this
		}
		
		def Options setTimeout(int timeout) {
			this.timeout = timeout
			return this
		}
		
		def Options setSystemPrompt(String prompt) {
			this.systemPrompt = systemPrompt
			return this
		}
		
		def Options setMaxInputs(int tokens) {
			this.maxInputTokens = tokens
			return this
		}
		
		def Options setMaxOutputs(int tokens) {
			this.maxOutputTokens = tokens
			return this
		}
	}
}