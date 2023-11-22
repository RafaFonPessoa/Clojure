(ns api
  (:require [clj-http.client :as http-client]
            [cheshire.core :as json]))

(def api-url "https://brapi.dev/api/quote/")
(def chave "h6PQv7nGo28jwg7UiePTEU")

;; Átomo para armazenar as transações
(def transacoes (atom {:compra [] :venda []}))

;;Obtem o Json da API
(defn obter-cotacao [codigo]
  (let [url (str api-url codigo "?token=" chave)
        response (http-client/get url)]
    (if (= 200 (:status response))
      (json/parse-string (:body response) true)
      (do
        (println (str "Erro na requisição: " (:status response)))
        nil))))

;; 1)
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
            (println (str (:stock stock) " - " (:name stock))))))
      (println (str "Erro na requisição: " (:status response))))))

;;2)
;;2)
(defn exibir-dados-acao [codigo]
  (let [cotacao (obter-cotacao codigo)]
    (if cotacao
      (let [results (:results cotacao)]
        (if (seq results)
          (do
            (println (str "Dados da Ação " codigo ":"))
            (println (str "Nome: " (:longName (first results))))
            (println (str "Código: " (:symbol (first results))))
            (println (str "Tipo de Ativo: " (:currency (first results))))
            (println (str "Descrição: " (:shortName (first results))))
            (println (str "Variação do Dia (R$): " (:regularMarketChange (first results))))
            (println (str "Variação do Dia (%): " (:regularMarketChangePercent (first results))))
            (println (str "Último Preço: " (:regularMarketPrice (first results))))
            (println (str "Preço Máximo: " (:regularMarketDayHigh (first results))))
            (println (str "Preço Mínimo: " (:regularMarketDayLow (first results))))
            (println (str "Preço de Abertura: " (:regularMarketOpen (first results))))
            (println (str "Preço de Fechamento: " (:regularMarketPreviousClose (first results))))
            (println (str "Hora: " (:regularMarketTime (first results))))
            (println "")))))))

;;3)
(defn registrar-compra [codigo quantidade valor]
  (swap! transacoes update-in [:compra] conj {:codigo codigo :quantidade quantidade :valor valor :data (java.util.Date.)}))

;;4)
(defn registrar-venda [codigo quantidade valor]
  (swap! transacoes update-in [:venda] conj {:codigo codigo :quantidade quantidade :valor valor :data (java.util.Date.)}))

;;6)
(defn exibir-transacoes-tipo [tipo]
  (let [transacoes-tipo (get @transacoes (keyword tipo))]
    (do
      (println tipo)
      (println transacoes-tipo)
      (if transacoes-tipo
        (do
          (println (str "Transações do tipo " tipo ":"))
          (doseq [transacao transacoes-tipo]
            (println (str "Código: " (:codigo transacao) ", Quantidade: " (:quantidade transacao) ", Valor: " (:valor transacao) ", Data: " (:data transacao)))))
        (println (str "Nenhuma transação do tipo " tipo " encontrada.")))))
    )


;;7)
(defn exibir-saldo-total []
  (let [compra (get @transacoes :compra)
        venda (get @transacoes :venda)]
    (println (str "Saldo Total da Carteira: " (- (apply + (map :valor compra)) (apply + (map :valor venda)))))))

(defn exibir-transacoes []
  (do
    (println "Transações de Compra:")
    (exibir-transacoes-tipo :compra)
    (println "")
    (println "Transações de Venda:")
    (exibir-transacoes-tipo :venda)))

;;5)
(defn exibir-extrato-completo []
  (do
    (exibir-transacoes)))


