class Trie:
    # word_end = -1

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.root = {}
        self.word_end = -1

    def insert(self, word):
        """
        Inserts a word into the trie.
        :type word: str
        :rtype: void
        """
        curNode = self.root
        for c in word:
            if not c in curNode:
                curNode[c] = {}
            curNode = curNode[c]

        curNode[self.word_end] = True

    def search(self, word):
        """
        Returns if the word is in the trie.
        :type word: str
        :rtype: bool
        """
        curNode = self.root
        for c in word:
            if not c in curNode:
                return False
            curNode = curNode[c]

        # Doesn't end here
        if self.word_end not in curNode:
            return False

        return True
