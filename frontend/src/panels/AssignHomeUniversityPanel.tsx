import { useEffect, useState } from 'react'
import Box from '@mui/material/Box'
import type { ReactElement } from 'react'
import { InputLabel, MenuItem, Radio, RadioGroup, Select } from '@mui/material'
import { sendGetUniverisitesFromCountryRequest } from '../utils/country'
import { FormControl, Button } from '@mui/material'
import { sendAssignHomeUniversityRequest } from '../utils/user'
import Alert from '@mui/material/Alert'
import { FormControlLabel } from '@mui/material'
import type { Country } from '../types/Country'
import type { GetUniversityResponse } from '../dtos/university/GetUniversityResponse'
import type { UniversityNameLanguage } from '../types/UniversityNameLanguage'
import type { AlertMessage } from '../types/AlertMessage'
import PanelHeader from '../components/PanelHeader'

type University = GetUniversityResponse

type AssignHomeUniversityPanelProps = {
  countries: Country[];
  getCountries: () => void;
}

const AssignHomeUniversityPanel = (props: AssignHomeUniversityPanelProps) => {
  const [selectedCountryId, setSelectedCountryId] = useState<number | null>(null)
  const [selectedUniversityId, setSelectedUniversityId] = useState<number | null>(null)
  const [selectedUniversityNameLanguage, setSelectedUniversityNameLanguage] =
    useState<UniversityNameLanguage>('english')
  const [universities, setUniversities] = useState<University[]>([])
  const [message, setMessage] = useState<AlertMessage | null>(null)

  const getUniversitiesFromCountry = async () => {
    if (selectedCountryId === null) {
      return
    }
    setUniversities([])
    const result = await sendGetUniverisitesFromCountryRequest(selectedCountryId)
    if (result.isSuccess) {
      setUniversities(result.data)
    } else {
      setMessage({type: 'error', content: 'Failed to load universities.'})
    }
  }

  const handleHomeUniversityAssignment = async () => {
    setMessage({type: 'info', content: 'Waiting for server response.'})
    const result = await sendAssignHomeUniversityRequest({universityId: selectedUniversityId})
    if (result.isSuccess) {
      setMessage({type: 'success', content: 'University was successfully assigned to user.'})
    } else {
      setMessage({type: 'error', content: 'Assigning user to university failed. Please try again later.'})
    }
  }

  const getInteractionElement = (universityId: number): ReactElement => {
    if ((selectedUniversityId === universityId) && (message === null)) {
      return (
        <Button variant='contained' size='small' sx={{marginLeft: 2}}
          onClick={handleHomeUniversityAssignment}>
          CONFIRM
        </Button>
      )
    }
    return <></>
  }

  useEffect(() => {
    props.getCountries()
    setTimeout(() => {
      if (props.countries.length === 0) {
        setMessage({type: 'error', content: 'Failed to load countries.'})
      }
    }, 5000)
  }, []) 

  useEffect(() => {
    getUniversitiesFromCountry()
  }, [selectedCountryId])

  useEffect(() => {
    setMessage(null)
  }, [selectedUniversityId, selectedCountryId])

  return (
    <Box sx={{display: 'flex', flexDirection: 'column'}}>
      <PanelHeader label='Assign home university to yourself'/>
      <Box sx={{display: 'flex', flexDirection: {xs: 'column', md:'row'}}}>
        <FormControl sx={{ m: 2, width: {xs: '100%', sm: '60%', lg: '40%'}}}>
          <InputLabel>Country</InputLabel>
          <Select id='select-country-id' autoWidth label='Country'
            value={selectedCountryId}
            onChange={e => setSelectedCountryId(Number(e.target.value))}>
            {props.countries.map(c => (
              c.id !== 0 ?
              <MenuItem key={c.id} value={c.id}>
                {c.name}
                <img src={`/flags/${c.name}.png`} 
                  style={{height: '0.8rem', marginLeft: 3}}/>
              </MenuItem> :
              <></>
            ))}
          </Select>
        </FormControl>
        <FormControl sx={{ m: 2, width: {xs: '50%', sm: '30%', lg: '20%'}}}>
          <InputLabel>University name language</InputLabel>
          <Select id='select-university-name-language-id' autoWidth 
            label='University name language'
            value={selectedUniversityNameLanguage}
            onChange={() => setSelectedUniversityNameLanguage(
              prevState => (prevState === 'english' ? 'native' : 'english'))}>
              <MenuItem key={'english'} value={'english'}>english</MenuItem>
              <MenuItem key={'native'} value={'native'}>native</MenuItem>
          </Select>
        </FormControl>
      </Box>
      {message != null ?
        <Alert variant='filled' severity={message.type} sx={{marginY: 2}}>
          {message.content}
        </Alert> : 
        <></>
      }
      <FormControl>
        <RadioGroup name='universities-buttons-group'
          value={selectedUniversityId}
          onChange={(event) => {
            setSelectedUniversityId(Number(event.target.value))
          }}>
          {universities.map(u => (
            <FormControlLabel key={u.id} value={u.id} control={<Radio/>} label={(
              <>
                {(selectedUniversityNameLanguage === 'english' && u.englishName) ?
                  u.englishName :
                  u.nativeName}
                {` [${u.city.name}]`}
                {getInteractionElement(u.id)}
              </>)}
          />))}
        </RadioGroup>
      </FormControl>
    </Box>
  )
}

export default AssignHomeUniversityPanel