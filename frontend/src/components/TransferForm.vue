<template>
    <div>
      <div class="form-container" :class="{ 'form-submitted': isFormSubmitted || errorMessage }">
        <transition name="fade" mode="out-in">
          <div :key="isFormSubmitted || errorMessage">
            <form v-if="!isFormSubmitted && !errorMessage" id="transfer-form" @submit.prevent="submitForm" :class="{ 'form-transition': !errorMessage }">
              <div class="input-container">
                <label for="sourceAccount">Conta de Origem</label>
                <input type="text" id="sourceAccount" name="contaOrigem" v-model="sourceAccount"
                  placeholder="Digite a conta de origem" class="bottom-border" required />
              </div>
              <div class="input-container">
                <label for="destinationAccount">Conta de Destino</label>
                <input type="text" id="destinationAccount" name="contaDestino" v-model="destinationAccount"
                  placeholder="Digite a conta de destino" class="bottom-border" required />
              </div>
              <div class="input-container">
                <label for="amount">Saldo</label>
                <input type="text" id="amount" name="saldo" v-model="amount" placeholder="Digite o saldo"
                  class="bottom-border" @input="validateAmount" required />
              </div>
              <div class="input-container">
                <label for="transferDate">Data de Transferência</label>
                <input type="date" id="transferDate" name="dataTransferencia" v-model="transferDate"
                  class="bottom-border" required />
              </div>
              <div class="input-container">
                <input type="submit" class="submit-btn" value="Registrar transferência" />
              </div>
            </form>
  
            <div v-if="successMessage || errorMessage" class="message-container">
              <div v-if="successMessage" class="success-message-container">
                <p class="success-message">{{ successMessage }}</p>
                <button class="submit-btn-re" @click="resetForm">Realizar outra transferência</button>
              </div>
  
              <div v-if="errorMessage" class="error-message-container">
                <p class="error-message">{{ errorMessage }}</p>
                <button class="submit-btn-re" @click="resetForm">Tentar novamente</button>
              </div>
            </div>
          </div>
        </transition>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "TransferForm",
    data() {
      return {
        sourceAccount: "",
        destinationAccount: "",
        amount: "",
        transferDate: "",
        isFormSubmitted: false,
        errorMessage: "",
        successMessage: "",
      };
    },
    methods: {
      submitForm() {
        this.errorMessage = "";
        this.successMessage = "";
  
        if (
          !this.sourceAccount ||
          !this.destinationAccount ||
          !this.amount ||
          this.amount <= 0 ||
          !this.transferDate
        ) {
          this.errorMessage = "Por favor, preencha todos os campos corretamente.";
          setTimeout(() => {
            this.errorMessage = "";
          }, 3000);
          return;
        }
  
        const formattedTransferDate = new Date(this.transferDate).toLocaleDateString("pt-BR");
  
        const formData = {
          sourceAccount: this.sourceAccount,
          destinationAccount: this.destinationAccount,
          amount: parseFloat(this.amount.replace(',', '.')),
          transferDate: formattedTransferDate,
        };
  
        axios
          .post("http://localhost:8081/v1/api/transfers", formData)
          .then((response) => {
            this.isFormSubmitted = true;
            this.successMessage = response.data.message || "Transferência agendada com sucesso!";
          })
          .catch((error) => {
            if (error.response && error.response.data) {
              this.errorMessage = error.response.data;
            } else {
              this.errorMessage = "Erro ao registrar transferência.";
            }
          });
      },
      resetForm() {
        this.sourceAccount = "";
        this.destinationAccount = "";
        this.amount = "";
        this.transferDate = "";
        this.isFormSubmitted = false;
        this.errorMessage = "";
        this.successMessage = "";
      },
      validateAmount() {
        this.amount = this.amount.replace(/[^0-9,.]/g, '');
  
        const decimalCount = (this.amount.match(/\./g) || []).length;
        if (decimalCount > 1) {
          this.amount = this.amount.slice(0, -1);
        }
      },
    },
  };
  </script>
  
  <style scoped>
  
  .form-container {
    max-width: 300px;
    margin: 0 auto;
    height: auto;
  }
  
  .form-transition {
    transition: opacity 0.5s;
  }
  
  .message-container {
    text-align: center;
    margin-top: 20px;
    font-weight: bold;
    margin-top: 40px;
    margin-bottom: 80px;
    font-size: 18px;
  }
  
  .success-message-container {
    color: green;
  }
  
  .error-message-container {
    color: red;
  }
  
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.5s;
  }
  
  .fade-enter,
  .fade-leave-to {
    opacity: 0;
  }
  
  #transfer-form {
    max-width: 300px;
    margin: 0 auto;
    height: auto;
  }
  
  .input-container {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
  }
  
  label {
    font-weight: bold;
    margin-bottom: 15px;
    color: #222;
    padding: 5px 10px;
    border-left: 4px solid #fcba03;
  }
  
  input.bottom-border {
    border: none;
    border-bottom: 1px solid #fcba03;
    padding: 5px 10px;
    width: 300px;
  }
  
  .submit-btn,
  .submit-btn-re {
    background-color: #222;
    color: #fcba03;
    font-weight: bold;
    border: 2px solid #222;
    padding: 10px;
    margin: 0 auto;
    cursor: pointer;
    transition: 0.5s;
  }
  
  .submit-btn-re {
    margin-top: 80px;
  }
  
  .submit-btn:hover,
  .submit-btn-re:hover {
    background-color: transparent;
    color: #222;
  }
  </style>
  