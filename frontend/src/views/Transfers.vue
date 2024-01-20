<template>
  <div class="main-container">
    <h1>TRANSFERÊNCIAS</h1>

    <div v-if="transfers.length === 0">
      <p>Carregando transferências...</p>
    </div>

    <div v-else>
      <h2>Lista de Transferências</h2>
      <table class="transfer-table">
        <thead>
          <tr>
            <th>Número</th>
            <th>Origem</th>
            <th>Destino</th>
            <th>Saldo</th>
            <th>Taxa %</th>
            <th>Data da Transferência</th>
            <th>Data do Agendamento</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transfer in transfers" :key="transfer.id">
            <td>{{ transfer.id }}</td>
            <td>{{ transfer.sourceAccount }}</td>
            <td>{{ transfer.destinationAccount }}</td>
            <td>{{ transfer.amount }}</td>
            <td>{{ transfer.fee }}</td>
            <td>{{ transfer.transferDate }}</td>
            <td>{{ transfer.schedulingDate }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
  
<script setup>
import { ref } from 'vue';
import axios from 'axios';

const { transfers } = useTransfers();

function useTransfers() {
  const transfers = ref([]);

  axios.get('http://localhost:8081/v1/api/transfers')
    .then((response) => {
      transfers.value = response.data.map((transfer, index) => ({ id: index + 1, ...transfer }));
    })
    .catch((error) => {
      console.error('Erro ao recuperar transferências:', error);
    });

  return { transfers };
}
</script>
  
<style scoped>
.main-container {
  text-align: center;
  margin: 20px;
}

.transfer-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.transfer-table th,
.transfer-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.transfer-table th {
  background-color: #111;
  color: #fff;
}

.transfer-table tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

.transfer-table tbody tr:hover {
  background-color: #e5e5e5;
}
</style>
  