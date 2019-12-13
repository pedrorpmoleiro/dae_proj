<template>
  <v-data-table
    :headers="headers"
    :items="payments"
    :items-per-page="5"
    class="elevation-1"
  />
</template>

<script>
  export default {
    data() {
      return {
        headers: [
          {text: 'Código', value: 'code'},
          {text: 'Username do sócio', value: 'socioUsername'},
          {text: 'Código de Produto', value: 'productCode'},
          {text: 'Data', value: 'date'},
          {text: 'Preço (€)', value: 'price'},
          {text: 'Quantidade', value: 'quantity'},
          {text: 'Estado', value: 'status'},
          {text: 'Recibo', value: 'receipt'},
        ],
        payments: [],
      }
    },
    mounted() {
      this.$axios.get('/api/payments').then((response) => {

        response.data.forEach((p) => {
          let date = new Date(0);
          date.setUTCSeconds(p.timestamp);
          p.date = date.toUTCString();
        });

        this.payments = response.data;
      });
    }
  }
</script>

<style scoped>

</style>
