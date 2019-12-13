<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="payments"
      :items-per-page="5"
      class="elevation-1"
      show-select
      single-select
      item-key="code"
      v-model="selected"
      loading-text="A carregar ..."
      :loading="loading"
    />
  </v-container>
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
        selected: undefined,
        loading: true
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
        this.loading = false;
      });
    }
  }
</script>

<style scoped>

</style>
