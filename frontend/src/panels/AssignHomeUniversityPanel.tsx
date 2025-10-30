import { useEffect, useState } from 'react'
import Box from '@mui/material/Box'
import type { ReactElement } from 'react'
import { InputLabel, MenuItem, Radio, RadioGroup, Select } from '@mui/material'
import { sendGetUniverisitesFromCountryRequest } from '../utils/country'
import { FormControl, Button } from '@mui/material'
import { sendAssignHomeUniversityRequest } from '../utils/user'
import { FormControlLabel } from '@mui/material'
import type { Country } from '../types/Country'
import type { UniversityNameLanguage } from '../types/UniversityNameLanguage'
import PanelHeader from '../components/PanelHeader'
import type { University } from '../types/University'
import { useSnackbar } from '../context/SnackBarContext'
import type { DataFetchStatus } from '../types/DataFetchStatus'
import LoadingContent from '../components/LoadingContent'
import ContentLoadError from '../components/ContentLoadError'

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
  const [universitiesFetchStatus, setUniversitiesFetchStatus] = 
    useState<DataFetchStatus | undefined>(undefined)
  const [countriesFetchStatus, setCountriesFetchStatus] = useState<DataFetchStatus>(
    props.countries.length != 0 ? 'success' : 'loading')
  const [showConfirmButton, setShowConfirmButton] = useState<boolean>(false)
  const { showAlert } = useSnackbar()

  const getUniversitiesFromCountry = async () => {
    if (selectedCountryId === null) {
      return
    }
    setUniversities([])
    setUniversitiesFetchStatus('loading')
    const result = await sendGetUniverisitesFromCountryRequest(selectedCountryId)
    if (result.isSuccess) {
      setUniversitiesFetchStatus('success')
      setUniversities(result.data)
    } else {
      setUniversitiesFetchStatus('connectionError')
    }
  }

  const handleHomeUniversityAssignment = async () => {
    setShowConfirmButton(false)
    showAlert('Waiting for server response.', 'info')
    const result = await sendAssignHomeUniversityRequest({universityId: selectedUniversityId})
    if (result.isSuccess) {
      showAlert('University was successfully assigned to user.', 'success')
    } else {
      showAlert('Assigning user to university failed. Please try again later.', 'error')
      setShowConfirmButton(true)
    }
  }

  const getInteractionElement = (universityId: number): ReactElement => {
    if ((selectedUniversityId === universityId) && showConfirmButton) {
      return (
        <Button variant='contained' size='small' sx={{marginLeft: 2}}
          onClick={handleHomeUniversityAssignment}>
          CONFIRM
        </Button>
      )
    }
    return <></>
  }

  const getUniversitiesContent = () => {
    switch (universitiesFetchStatus) {
      case undefined:
        return <></>
      case 'loading':
        return <LoadingContent title='Loading universities'/>  
      case 'connectionError':
        return <ContentLoadError title='Connection error' subheader='Failed to load universities'/>
      case 'serverError':
        return <ContentLoadError title='Server error' subheader='Failed to load universities'/>
      case 'success':
        return (
          <FormControl>
            <RadioGroup name='universities-buttons-group'value={selectedUniversityId}
              onChange={event => setSelectedUniversityId(Number(event.target.value))}>
              {universities.map(u => (
                <FormControlLabel key={u.id} value={u.id} control={<Radio/>} label={(
                  <>
                    {(selectedUniversityNameLanguage === 'english' && u.englishName) ?
                      u.englishName : u.nativeName}
                    {` [${u.city.name}]`}
                    {getInteractionElement(u.id)}
                  </>)}
              />))}
            </RadioGroup>
          </FormControl>)
    }
  }

  useEffect(() => {
    props.getCountries()
  }, [])

  useEffect(() => {
    const timeout = setTimeout(() => {
      if (props.countries.length === 0) {
        setCountriesFetchStatus('connectionError')
      } else {
        setCountriesFetchStatus('success')
      }
    }, 5000)

    return () => clearTimeout(timeout)
  }, [])

  useEffect(() => {
    getUniversitiesFromCountry()
  }, [selectedCountryId])

  useEffect(() => {
    setShowConfirmButton(true)
  }, [selectedUniversityId])

  const getContent = () => {
    switch (countriesFetchStatus) {
      case 'success':
        return (
          <>
            <Box sx={{display: 'flex', flexDirection: {xs: 'column', md:'row'}}}>
              <FormControl sx={{ m: 2, width: {xs: '100%', sm: '60%', lg: '40%'}}}>
                <InputLabel>Country</InputLabel>
                <Select id='select-country-id' autoWidth label='Country'
                  value={selectedCountryId}
                  onChange={e => setSelectedCountryId(Number(e.target.value))}>
                  {props.countries.map(c => (
                    c.id !== 0 ? (
                      <MenuItem key={c.id} value={c.id}>
                        {c.name}
                        <img src={`/flags/${c.name}.png`} style={{height: '0.8rem', marginLeft: 3}} />
                      </MenuItem>
                    ) : null
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
            {getUniversitiesContent()}
          </>
        )
      case 'loading':
        return <LoadingContent title='Loading countries'/>  
      case 'connectionError':
        return <ContentLoadError title='Connection error' subheader='Failed to load countries'/>
      case 'serverError':
        return <ContentLoadError title='Server error' subheader='Failed to load countries'/>
    }
  }

  return (
    <Box sx={{display: 'flex', flexDirection: 'column'}}>
      <PanelHeader label='Assign home university to yourself'/>
      {getContent()}
    </Box>
  )
}

export default AssignHomeUniversityPanel