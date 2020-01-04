<template>
    <div>
        <h3>Assitude</h3>
        <v-data-table hide-default-footer  :items="escaloes" :headers="fields">
            <template v-slot:item.action="{item}">
                <v-btn :to="`/assiduidade/${item.epoca}/${item.modalidade}/${item.name}/horario`"color="green">assiduidade</v-btn>
            </template>
        </v-data-table>
    </div>
</template>

<script>
    export default {
     data:function () {
       return{
           escaloes:[],
           fields:[
               {text:'Epoca',value:'epoca'},
               {text:'Modalidade',value:'modalidade'},
               {text:'Escalao',value:'name'},
               {text:'Acciones',value:'action'}
           ]
       }
     },
        mounted() {
                this.username='profe1';
                this.getEscaloes();
        },
        methods:{
         getEscaloes(){
             this.$axios.get('/api/modalidades/' + this.username+'/escaloes/treinador')
                 .then(horario => {
                     this.escaloes=horario.data.escaloes;
                 })
         }
        }
    }
</script>

<style scoped>

</style>