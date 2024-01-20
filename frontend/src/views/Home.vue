<template>
    <div class="main-container">  
        <h1>Faça o Agendamento da sua Transferência</h1>
        <TransferForm />
    </div>
</template>

<script>
    import TransferForm from '@/components/TransferForm.vue';

    export default {
        name: "Home",
        components: {
            TransferForm
        }
    }
</script>
