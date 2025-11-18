package com.example.project_verbal

// Clean input to remove spaces and punctuation
fun extractWords(input: String): List<String> {
    return input
        .replace(Regex("[^\\w\\s]"), "") // Remove punctuation
        .trim()
        .split("\\s+".toRegex())
        .map { it.lowercase() } // Word strings as elements in an array
}


// Find words to filter out, such as "and," "or," "but," etc.
// using an Aho-Corasick keyword search algorithm
class TrieNode {
    val children = mutableMapOf<Char, TrieNode>()
    var isEndOfWord = false
    var word: String? = null
    var fail: TrieNode? = null
    val output = mutableListOf<String>()
}

class AhoCorasick(private val keywords: List<String>) {
    private val root = TrieNode()

    init {
        buildTrie()
        buildFailureLinks()
    }

    private fun buildTrie() {
        for (word in keywords) {
            var node = root
            for (char in word) {
                node = node.children.computeIfAbsent(char) { TrieNode() }
            }
            node.isEndOfWord = true
            node.word = word
            node.output.add(word)
        }
    }

    private fun buildFailureLinks() {
        val queue = ArrayDeque<TrieNode>()
        root.fail = root
        queue += root.children.values

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            for ((char, child) in current.children) {
                var failNode = current.fail
                while (failNode != null && failNode != root && !failNode.children.containsKey(char)) {
                    failNode = failNode.fail
                }
                child.fail = failNode?.children?.get(char) ?: root

                child.output += child.fail!!.output
                queue += child
            }
        }
    }

    fun filter(words: List<String>): List<String> {
        val result = mutableListOf<String>()
        for (word in words) {
            var node = root
            for (char in word) {
                while (node != root && !node.children.containsKey(char)) {
                    node = node.fail!!
                }
                node = node.children[char] ?: root
            }
            if (node.output.isEmpty()) {
                result.add(word)
            }
        }
        return result
    }
}

// Count and display most common words
fun countFrequencies(words: List<String>): Map<String, Int> {
    return words.groupingBy { it }.eachCount()
}

// Example main
//fun main() {
//val input = "The quick brown fox jumps over the lazy dog. And if the dog barks, or runs, it’s fine."
//val stopwords = listOf("the", "and", "or", "if", "it", "is", "a", "an", "in", "on", "at", "to", "of", "for")

//val words = extractWords(input)
//val ac = AhoCorasick(stopwords)
//val filtered = ac.filter(words)
//val frequencies = countFrequencies(filtered)
//val sorted = frequencies.entries.sortedByDescending { it.value }

//println("Most common words (excluding stopwords):")
//for ((word, count) in sorted) {
//println("$word: $count")
//    }
//}