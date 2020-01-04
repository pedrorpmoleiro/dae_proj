<template>
  <v-content>
    <v-dialog v-model="dialogSocio" width="400px">
      <v-card>
        <v-card-title>
          Inscrever nuevo {{textSocio}}
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="15" md="6">
                <v-text-field v-model="username" label="Epoca nome"/>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer/>
          <v-btn color="blue darken-1" text @click="dialogSocio=false">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="inscreverSocio">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-container>
      <v-btn :to="'/modalidades'" color="cyan">Voltar</v-btn>
      <h3>{{this.$route.params.modalidade}}</h3>
      <v-row>
        <v-col>
          <v-btn @click="inscreverSocioBtn('a')" class="primary">Inscrever Atleta</v-btn>
          <v-card>
            <v-card-title>Atletas: {{escalao.name}}</v-card-title>
            <v-data-table v-if="atletasC.length" :items="atletasC" :headers="atletasFields">
            </v-data-table>
          </v-card>
        </v-col>
        <v-col>
          <v-btn @click="inscreverSocioBtn('t')" class="primary">Inscrever Treinador</v-btn>
          <v-card>
            <v-card-title>Treinadores: {{escalao.name}}</v-card-title>
            <v-data-table v-if="treinadoresC.length" :items="treinadoresC" :headers="atletasFields">
            </v-data-table>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-content>
</template>

<script>
  export default {
    data() {
      return {
        escalao: {},
        atletasFields: [
          {text: 'Nome', value: 'name'},
          {text: 'Username', value: 'username'},
          {text: 'Email', value: 'email'}
        ],
        dialogSocio: false,
        username: '',
        textSocio: '',
        type: ''
      }
    },
    computed: {
      atletasC() {

        return this.escalao.atletas || []
      },
      treinadoresC() {
        return this.escalao.treinadores || []
      }
    },
    created() {
      var epoca = this.$route.params.epoca;
      var modalidade = this.$route.params.modalidade;
      var escalao = this.$route.params.escalao;
      console.log(epoca)
      this.$axios.get('/api/modalidades/' + epoca + '/' + modalidade + '/' + escalao + '/socios')
        .then(escalao => this.escalao = escalao.data || {})

    },
    methods: {
      inscreverSocioBtn(tipo) {
        this.username = ''
        switch (tipo) {
          case 'a':
            this.textSocio = 'Atleta'
            this.type = 'a'
            break;
          case 't':
            this.textSocio = 'Treinador'
            this.type = 't'
            break;
        }
        this.dialogSocio = true;
      },
      inscreverSocio() {
        var epoca = this.$route.params.epoca;
        var modalidade = this.$route.params.modalidade;
        var escalao = this.$route.params.escalao;
        var route = '';
        switch (this.type) {
          case 'a':
            route = epoca + '/' + modalidade + '/' + escalao + '/' + this.username + '/atleta'
            break;
          case 't':
            route = epoca + '/' + modalidade + '/' + escalao + '/' + this.username + '/treinador'
            break;
        }
        this.$axios({
          method: 'post',
          url: '/api/modalidades/' + route,
          headers: {'Content-Type': 'application/json'}
        }).then(escalao => {
          this.escalao = escalao.data
          this.dialogSocio = false;
          this.username = '';
        })
      }
    }
  }
</script>

<style scoped>

</style>
