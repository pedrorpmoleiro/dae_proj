<template>
  <v-container>
    <v-card-title>
      Pagamentos
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="search"
        label="Search"
        single-line
        hide-details
      />
    </v-card-title>
    <v-data-table
      :search="search"
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
    >
      <template v-slot:item.action="{ item }">
        <v-icon
          small
          class="mr-2"
          @click="viewPayment(item)"
        >
          edit
        </v-icon>
        <v-icon
          small
          @click="deletePayment(item)"
        >
          delete
        </v-icon>
      </template>
    </v-data-table>
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
        loading: true,
        search: '',
      }
    },
    methods: {
      deletePayment(item) {
        console.log(this.selected[0]);
      },
      viewPayment(item) {

      }
    },
    mounted() {
      this.$axios.get('/api/payments').then((response) => {

        response.data.forEach((p) => {
          let date = new Date(0);
          date.setUTCSeconds(p.timestamp);
          p.date = date.toUTCString();

          if (p.status == 'PAID') {
            p.status = "Pago";
          } else {
            p.status = "Não Pago";
          }
        });

        this.payments = response.data;
        this.loading = false;
      });
    }
  }
</script>

<style scoped>

</style>
