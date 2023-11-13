(ns sua-app
  (:require [clj-http.client :as http-client]
            [cheshire.core :as json]))

(def api-url "https://brapi.dev/api/quote/")
(def chave "h6PQv7nGo28jwg7UiePTEU")

(defn obter-cotacao [codigo]
  (let [url (str api-url codigo "?token=" chave)
        response (http-client/get url)]
    (if (= 200 (:status response))
      (json/parse-string (:body response) true)
      (do
        (println (str "Erro na requisição: " (:status response)))
        nil))))

(defn exibir-lista-companhias []
  (let [url (str api-url "list?token=" chave)
        response (http-client/get url)]
    (if (= 200 (:status response))
      (do
        (println "Lista de Companhias Cotadas:")
        (let [data (json/parse-string (:body response) true)
              indexes (get-in data [:indexes])
              stocks (get-in data [:stocks])]
          (doseq [index indexes]
            (println (str (:stock index) " - " (:name index))))
          (doseq [stock stocks]
            (println (str (:stock stock) " - " (:name stock)))))
        )
      (println (str "Erro na requisição: " (:status response)))))
  )


;; Função para exibir os dados de uma ação escolhida
(defn exibir-dados-acao [codigo]
  (let [cotacao (obter-cotacao codigo)]
    (if cotacao
      (do
        (println (str "Dados da Ação " codigo ":"))
        (println (str "Nome: " (:longName (first (:results cotacao)))))
        (println (str "Código: " (:symbol (first (:results cotacao)))))
        (println (str "Tipo de Ativo: " (:currency (first (:results cotacao)))))
        (println (str "Descrição: " (:shortName (first (:results cotacao)))))
        (println (str "Variação do Dia (R$): " (:regularMarketChange (first (:results cotacao)))))
        (println (str "Variação do Dia (%): " (:regularMarketChangePercent (first (:results cotacao)))))
        (println (str "Último Preço: " (:regularMarketPrice (first (:results cotacao)))))
        (println (str "Preço Máximo: " (:regularMarketDayHigh (first (:results cotacao)))))
        (println (str "Preço Mínimo: " (:regularMarketDayLow (first (:results cotacao)))))
        (println (str "Preço de Abertura: " (:regularMarketOpen (first (:results cotacao)))))
        (println (str "Preço de Fechamento: " (:regularMarketPreviousClose (first (:results cotacao)))))
        (println (str "Hora: " (:regularMarketTime (first (:results cotacao)))))
        )
      (println (str "Ação não encontrada: " codigo)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; RODANDO A APLICAÇÃO ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn menu-principal []
  (loop []
    (println "   ____           _       _                 _           _        /\\/|")
    (println "  / ___|__ _ _ __| |_ ___(_)_ __ __ _    __| | ___     / \\   ___|/\\/   ___  ___")
    (println " | |   / _` | '__| __/ _ \\ | '__/ _` |  / _` |/ _ \\   / _ \\ / __/ _ \\ / _ \\/ __|")
    (println " | |__| (_| | |  | ||  __/ | | | (_| | | (_| |  __/  / ___ \\ (_| (_) |  __/\\__ \\")
    (println "  \\____\\__,_|_|   \\__\\___|_|_|  \\__,_|  \\__,_|\\___| /_/   \\_\\___\\___/ \\___||___/")
    (println "                                                             )_)")
    (println "----------------------------------------------------")
    (println "Escreva o número para selecionar a opção desejada:")
    (println "1) Ver Lista Completa de Companhias cadastradas na B3")
    (println "2) Exibir dados da ação de uma companhia")
    (println "3) Sair")
    (println "----------------------------------------------------")
    (println "Sau Escolha: ")

    (let [selecao (Integer/parseInt (read-line))]
      (cond
        (= selecao 1) (do
                        (exibir-lista-companhias)
                        (recur)) ;; Adicionado o recur para continuar o loop
        (= selecao 2) (do
                        (println "Digite o nome da companhia:")
                        (exibir-dados-acao (read-line))
                        (recur)) ;; Adicionado o recur para continuar o loop
        (= selecao 3) (println "Saindo...")
        :else (do
                (println "Opção inválida. Tente novamente.")
                (recur))))))

(menu-principal)


