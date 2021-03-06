(ns reddit.format
  (require [clojure.string :as str]))

(defn quotify
  "Format the text as a markdown quote, i.e.
  each new paragraph beginning with '> '."
  [& s]
  (clojure.string/replace (apply str s) #"(\A|\n\n)(.+)" "$1> $2"))

(defn superscript
  "^Superscript each word."
  [& s]
  (-> (apply str s)
      (str/replace #"([^ \[*]+)" "^$1")
      (str/replace "^(" "^\\(")))

(defn superscript-n
  "Apply superscript n times."
  [n & s] (nth (iterate superscript (apply str s)) n))

(defn hyperlink
  "Create markdown link."
  [s url]
  (str "[" s "](" url ")"))

(defn italic
  "Wrap the given text in asterisks."
  [& s]
	(str "*" (apply str s) "*"))

(defn paragraphs
  "Create a comment with each argument
  as a seperate paragraph."
  [& ss] (->> ss (filter identity) (str/join "\n\n")))

(def line "---")

(def --- line)
(def i italic)
(def ss superscript)
(def sn superscript-n)
(def l hyperlink)
(def p paragraphs)
(def q quotify)
