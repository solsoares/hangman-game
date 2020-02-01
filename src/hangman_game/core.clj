(ns hangman-game.core
  (:gen-class))

(def total-lives 6)
(def secret-word "WATERMELON")

(defn lose-the-game [] (println "Sorry, you lose!"))
(defn win-the-game [] (println "Congratulations, you win!"))

(defn missing-letters
  [word hits]
  (remove (fn [letter] (contains? hits (str letter))) word))

(defn hit-the-whole-word?
  [word hits]
  (empty? (missing-letters word hits)))

(defn read-letter! [] (read-line))

(defn hit?
  [kick word]
  (.contains word kick))

(defn print-game [lives word hits]
  (println "Lives " lives)
  (doseq [letter (seq word)]
    (if (contains? hits (str letter))
      (print letter " ") (print "_" " ")))
  (println))

(defn game [lives word hits]
  (print-game lives word hits)
  (cond
    (= lives 0) (lose-the-game)
    (hit-the-whole-word? word hits) (win-the-game)
    :else
    (let [kick (read-letter!)]
      (if (hit? kick word)
        (do
          (println "You hit the letter! :)")
          (recur lives word (conj hits kick)))
        (do
          (println "Wrong letter! You lost a life! :(")
          (recur (dec lives) word hits))))))

(defn start-the-game []
  (println "Start the game!")
  (game total-lives secret-word #{}))

(defn -main [& args]
  (start-the-game))
